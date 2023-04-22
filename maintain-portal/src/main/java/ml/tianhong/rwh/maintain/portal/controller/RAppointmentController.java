package ml.tianhong.rwh.maintain.portal.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import ml.tianhong.rwh.maintain.common.api.ResultVO;
import ml.tianhong.rwh.maintain.portal.service.RAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;

/**
 * <p>
 * 前端控制器
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
     * @param request       请求对象，校验token，避免横向越权
     * @param carId
     * @param dateRecord    预约日期
     * @param timeId        预约时间段标识
     * @param serviceIdList 选择的服务列表，前端传入json序列化后的String数组对象
     * @param remark        备注和描述
     * @return
     */
    @ApiOperation("新增预约控制器")
    @PostMapping("/addAppointment")
    public ResultVO addAppointment(HttpServletRequest request,
                                   @RequestParam String carId,
                                   @RequestParam @ApiParam("只记录预约具体日期，传入参数格式为：2020/06/17 12:00:00") Date dateRecord,
                                   @RequestParam @ApiParam("记录预约具体时间段Id") String timeId,
                                   @RequestParam @ApiParam("前端传入参数格式 &serviceIdList=1638411083733790721&serviceIdList=1638416747424014337 ") ArrayList<String> serviceIdList,
                                   @RequestParam(required = false) String remark) {
        return rAppointmentService.addAppointment(request, carId, dateRecord, timeId, serviceIdList, remark);
    }

    @ApiOperation("根据用户token获取用户预约控制器")
    @PostMapping("/getAppointments")
    public ResultVO getAppointments(HttpServletRequest request){
        return rAppointmentService.getAppointments(request);
    }

    @ApiOperation("根据传入appointmentId和request验证后删除预约")
    @DeleteMapping("/deleteAppointment")
    public ResultVO deleteAppointment(HttpServletRequest request, String appointmentId){
        return rAppointmentService.deleteAppointment(request,appointmentId);
    }
}

