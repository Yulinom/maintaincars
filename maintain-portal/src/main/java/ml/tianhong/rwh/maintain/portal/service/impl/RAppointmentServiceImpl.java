package ml.tianhong.rwh.maintain.portal.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import ml.tianhong.rwh.maintain.common.api.ResultVO;
import ml.tianhong.rwh.maintain.common.util.JwtUtils;
import ml.tianhong.rwh.maintain.portal.entity.RAppointment;
import ml.tianhong.rwh.maintain.portal.mapper.RAppointmentMapper;
import ml.tianhong.rwh.maintain.portal.service.RAppointmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ml.tianhong.rwh.maintain.portal.service.RAsService;
import ml.tianhong.rwh.maintain.portal.service.RCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author rwh
 * @since 2023-03-20
 */
@Service
public class RAppointmentServiceImpl extends ServiceImpl<RAppointmentMapper, RAppointment> implements RAppointmentService {

    @Autowired
    private RCarService rCarService;

    @Autowired
    private RAsService rAsService;

    @Override
    public ResultVO addAppointment(HttpServletRequest request, String carId, Date time, ArrayList<String> serviceIdList, String remark) {
        if (serviceIdList == null
                || StringUtils.isEmpty((String) request.getSession().getAttribute("token"))
                || StringUtils.isEmpty(carId)
                || time == null)
            return ResultVO.error().message("非法请求");


        if (rCarService.getCarById(carId, request).getSuccess()) {
            //先添加预约记录，拿到新生成的预约id
            RAppointment rAppointment = new RAppointment();

            rAppointment.setCarId(carId).setStatus("0").setTime(time).setRemark(remark);
            baseMapper.insert(rAppointment);
            System.out.println(rAppointment);

            //添加预约和服务的多对多关系记录
            for (Object o : serviceIdList) {
                rAsService.addRAs(rAppointment.getId(), (String) o);
            }

            return ResultVO.ok();
        }

        return ResultVO.error();
    }

    public ResultVO addAppointment(HttpServletRequest request, String carId, Date time, ArrayList<String> serviceIdList, String remark, String phone, String registrationNumber) {
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(registrationNumber)) {
            return ResultVO.error().message("非法请求");
        }
        remark += "\t\t***临时用户的电话为：" + phone + "\n\t\t***车牌号为：" + registrationNumber;

        //向request的session中注入token
//        request.getSession().setAttribute("token", JwtUtils.getJwtToken("admin"));

        return addAppointment(request, carId, time, serviceIdList, remark);
    }
}
