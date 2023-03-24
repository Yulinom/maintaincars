package ml.tianhong.rwh.maintain.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import ml.tianhong.rwh.maintain.common.api.ResultVO;
import ml.tianhong.rwh.maintain.portal.entity.RTimeSet;
import ml.tianhong.rwh.maintain.portal.mapper.RTimeSetMapper;
import ml.tianhong.rwh.maintain.portal.service.RTimeSetService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author rwh
 * @since 2023-03-24
 */
@Service
public class RTimeSetServiceImpl extends ServiceImpl<RTimeSetMapper, RTimeSet> implements RTimeSetService {

    @Override
    public ResultVO addTimeSet(Date startTime, Date endTime) {
        if (startTime != null && endTime != null) {
            if (endTime.before(startTime)) return ResultVO.error().message("结束时间不能早于开始时间");
            else {
                baseMapper.insert(new RTimeSet().setTimeStart(startTime).setTimeEnd(endTime));
                return ResultVO.ok();
            }
        } else return ResultVO.error().message("请填写完整");
    }

    @Override
    public ResultVO getTimeSets() {
        return ResultVO.ok().data("data", baseMapper.selectList(null));
    }

    @Override
    public ResultVO getTimeSetById(String id) {
        if (StringUtils.isEmpty(id)) return ResultVO.error().message("非法请求");
        return ResultVO.ok().data("data",baseMapper.selectOne(new QueryWrapper<RTimeSet>().eq("id",id)));
    }
}
