package ml.tianhong.rwh.maintain.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import ml.tianhong.rwh.maintain.common.api.ResultVO;
import ml.tianhong.rwh.maintain.portal.entity.RService;
import ml.tianhong.rwh.maintain.portal.mapper.RServiceMapper;
import ml.tianhong.rwh.maintain.portal.service.RServiceService;
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
public class RServiceServiceImpl extends ServiceImpl<RServiceMapper, RService> implements RServiceService {

    @Override
    public ResultVO addService(String sName) {
        baseMapper.insert(new RService().setSName(sName));
        return ResultVO.ok();
    }

    @Override
    public ResultVO getServices() {
        return ResultVO.ok().data("data",baseMapper.selectList(null));
    }

    @Override
    public ResultVO getServiceById(String id) {
        if (StringUtils.isEmpty(id)) return ResultVO.error().message("非法请求");
        return ResultVO.ok().data("data",baseMapper.selectOne(new QueryWrapper<RService>().eq("id",id)));
    }
}
