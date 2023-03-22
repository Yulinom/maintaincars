package ml.tianhong.rwh.maintain.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import ml.tianhong.rwh.common.api.ResultVO;
import ml.tianhong.rwh.maintain.portal.entity.RBrand;
import ml.tianhong.rwh.maintain.portal.mapper.RBrandMapper;
import ml.tianhong.rwh.maintain.portal.service.RBrandService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class RBrandServiceImpl extends ServiceImpl<RBrandMapper, RBrand> implements RBrandService {

    @Override
    public ResultVO addBrand(String brand) {
        if (!StringUtils.isEmpty(brand)) {
            QueryWrapper<RBrand> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("brand",brand);
            if (baseMapper.selectCount(queryWrapper) <= 0) {
                baseMapper.insert(new RBrand().setBrand(brand));
                return ResultVO.ok();
            }
            return ResultVO.error().message("品牌已经存在");
        }
        return ResultVO.error().message("品牌名不能为空");
    }

    @Override
    public ResultVO getBrandByName(String brand) {
        return ResultVO.ok().data("data",baseMapper.selectOne(new QueryWrapper<RBrand>().eq("brand",brand)));
    }
}
