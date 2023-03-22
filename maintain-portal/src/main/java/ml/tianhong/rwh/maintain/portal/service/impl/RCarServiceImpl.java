package ml.tianhong.rwh.maintain.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import ml.tianhong.rwh.maintain.common.api.ResultVO;
import ml.tianhong.rwh.maintain.portal.entity.RCar;
import ml.tianhong.rwh.maintain.portal.entity.bo.CarBO;
import ml.tianhong.rwh.maintain.portal.entity.vo.CarVO;
import ml.tianhong.rwh.maintain.portal.mapper.RCarMapper;
import ml.tianhong.rwh.maintain.portal.service.RCarModelService;
import ml.tianhong.rwh.maintain.portal.service.RCarService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ml.tianhong.rwh.maintain.common.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author rwh
 * @since 2023-03-20
 */
@Service
public class RCarServiceImpl extends ServiceImpl<RCarMapper, RCar> implements RCarService {

    @Autowired
    private RCarModelService rCarModelService;

    @Override
    public ResultVO addCar(String token, CarVO car) {
        RCar rCar = new RCar();
        rCar.setCustomerId(car.getCustomerId())
                .setLicenseSpace(car.getLicenseSpace())
                .setRegistrationNumber(car.getRegistrationNumber())
                .setModelId(car.getModelId())
                .setCustomerId(JwtUtils.getMemberIdByJwtToken(token));
        if (baseMapper.insert(rCar) > 0) return ResultVO.ok().message("车辆添加成功");
        return ResultVO.error().message("车辆添加失败");
    }

    @Override
    public ResultVO getCarsByToken(HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute("token");
        System.out.println(token);
        if (StringUtils.isEmpty(token)) return ResultVO.error().message("请先登录");
        String id = JwtUtils.getMemberIdByJwtToken(token);
        System.out.println(id);
        if (StringUtils.isEmpty(id)) return ResultVO.error().message("您暂未添加车辆");
        List<RCar> cars = baseMapper.selectList(new QueryWrapper<RCar>().eq("customer_id", id));
        ArrayList<CarBO> carBOArrayList = new ArrayList<>();
        for (RCar car : cars) {
            CarBO carBO = new CarBO(car).setModel(rCarModelService.getById(car.getModelId()));
            carBOArrayList.add(carBO);
        }
        return ResultVO.ok().data("data", carBOArrayList);
    }

    @Override
    public ResultVO getCarById(String id, HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute("token");
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(token)) {
            return ResultVO.error().message("非法请求");
        }
        RCar rCar = baseMapper.selectOne(new QueryWrapper<RCar>().eq("id", id));
        if(rCar != null){
            if (rCar.getCustomerId().equals(JwtUtils.getMemberIdByJwtToken(token))) return ResultVO.ok().data("data", rCar);
        }
        return ResultVO.error().message("请求非法");
    }

    @Override
    public ResultVO deleteCarById(HttpServletRequest request, String id) {
        String uid = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(uid) || StringUtils.isEmpty(id)) {
            return ResultVO.error().message("参数不能为空");
        } else {
            if (baseMapper.delete(new QueryWrapper<RCar>().eq("id", id).eq("customer_id", uid)) > 0) {
                return ResultVO.ok();
            }
        }
        return ResultVO.error().message("非法请求");
    }
}
