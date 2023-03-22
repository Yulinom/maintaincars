package ml.tianhong.rwh.maintain.portal.service;

import ml.tianhong.rwh.common.api.ResultVO;
import ml.tianhong.rwh.maintain.portal.entity.RAppointment;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rwh
 * @since 2023-03-20
 */
public interface RAppointmentService extends IService<RAppointment> {

    ResultVO addAppointment(HttpServletRequest request, String carId, Date time, String serviceIdList, String remark);
}
