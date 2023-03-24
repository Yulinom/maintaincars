package ml.tianhong.rwh.maintain.portal.service;

import ml.tianhong.rwh.maintain.common.api.ResultVO;
import ml.tianhong.rwh.maintain.portal.entity.RAppointment;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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

    ResultVO addAppointment(HttpServletRequest request, String carId, Date time, String timeId, ArrayList<String> serviceIdList, String remark);

    /**
     *
     * 未经过预约，直接到店顾客进行预约
     * @param request
     * @param carId
     * @param dateRecord
     * @param timeId
     * @param serviceIdList
     * @param remark
     * @param phone
     * @param registrationNumber
     * @return
     */
    ResultVO addAppointment(HttpServletRequest request, String carId, Date dateRecord, String timeId, ArrayList<String> serviceIdList, String remark, String phone, String registrationNumber);

    ResultVO getAppointments(HttpServletRequest request);

    ResultVO getAppointmentsByCarId(HttpServletRequest request, String carId);

    ResultVO deleteAppointment(HttpServletRequest request, String appointmentId);
}
