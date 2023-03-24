package ml.tianhong.rwh.maintain.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import ml.tianhong.rwh.maintain.common.api.ResultVO;
import ml.tianhong.rwh.maintain.portal.entity.RCarModel;
import ml.tianhong.rwh.maintain.portal.mapper.RCarModelMapper;
import ml.tianhong.rwh.maintain.portal.service.RBrandService;
import ml.tianhong.rwh.maintain.portal.service.RCarModelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rwh
 * @since 2023-03-20
 */
@Service
public class RCarModelServiceImpl extends ServiceImpl<RCarModelMapper, RCarModel> implements RCarModelService {

    @Autowired
    private RBrandService rBrandService;

    @Override
    public ResultVO addModel(String model, String brand) {
        if (StringUtils.isEmpty(model) || StringUtils.isEmpty(brand)) return ResultVO.error().message("请填写完整");
        else {
            if (rBrandService.getBrandByName(brand) == null) return ResultVO.error().message("品牌不存在");
            QueryWrapper<RCarModel> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("model",model).eq("brand",brand);
            if (baseMapper.selectCount(queryWrapper) <= 0) {
                baseMapper.insert(new RCarModel().setBrand(brand).setModel(model));
                return ResultVO.ok().message("添加车型成功");
            }
        }
        return ResultVO.error().message("车型已经存在");
    }

    @Override
    public ResultVO getModelById(String id) {
        RCarModel select = baseMapper.selectById(id);
        if (select != null) {
            return ResultVO.ok().data("data", select);
        }
        return ResultVO.error();
    }

    @Override
    public ResultVO getModels() {
        return ResultVO.ok().data("data",baseMapper.selectList(null));
    }
}
