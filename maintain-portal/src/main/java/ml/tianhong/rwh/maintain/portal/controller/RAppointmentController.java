package ml.tianhong.rwh.maintain.portal.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ml.tianhong.rwh.common.api.ResultVO;
import ml.tianhong.rwh.maintain.portal.service.RAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rwh
 * @since 2023-03-20
 */
@Api("预约Controller")
@RestController
@RequestMapping("/portal/r-appointment")
public class RAppointmentController {

    @Autowired
    private RAppointmentService rAppointmentService;

    /**
     *
     *
     * @param request 请求对象，校验token，避免横向越权
     * @param carId
     * @param time 预约时间
     * @param serviceIdList 选择的服务列表
     * @param remark 备注和描述
     * @return
     */
    @ApiOperation("新增预约控制器，")
    public ResultVO addAppointment(HttpServletRequest request, String carId, Date time, String serviceIdList, String remark){
        return rAppointmentService.addAppointment(carId,time,serviceIdList,remark);
    }
}

