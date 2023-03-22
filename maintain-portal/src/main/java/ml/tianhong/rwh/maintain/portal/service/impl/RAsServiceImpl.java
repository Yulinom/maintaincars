package ml.tianhong.rwh.maintain.portal.service.impl;

import ml.tianhong.rwh.maintain.common.api.ResultVO;
import ml.tianhong.rwh.maintain.portal.entity.RAs;
import ml.tianhong.rwh.maintain.portal.mapper.RAsMapper;
import ml.tianhong.rwh.maintain.portal.service.RAsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rwh
 * @since 2023-03-22
 */
@Service
public class RAsServiceImpl extends ServiceImpl<RAsMapper, RAs> implements RAsService {

    @Override
    public ResultVO addRAs(String appointmentId, String serviceId) {
        RAs rAs = new RAs().setAppointmentId(appointmentId).setServiceId(serviceId);
        baseMapper.insert(rAs);
        return ResultVO.ok();
    }
}
