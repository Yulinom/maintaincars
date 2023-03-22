package ml.tianhong.rwh.maintain.portal.controller;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import ml.tianhong.rwh.maintain.common.api.ResultVO;
import ml.tianhong.rwh.maintain.portal.service.RAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
     * @param time          预约时间
     * @param serviceIdList 选择的服务列表，前端传入json序列化后的String数组对象
     * @param remark        备注和描述
     * @return
     */
    @ApiOperation("新增预约控制器，")
    @PostMapping("/addAppointment")
    public ResultVO addAppointment(HttpServletRequest request,
                                   @RequestParam String carId,
                                   @RequestParam @ApiParam("前端传入参数格式 Wed Mar 22 13:40:50 CST 2023") Date time,
                                   @RequestParam @ApiParam("前端传入参数格式 &serviceIdList=1638411083733790721&serviceIdList=1638416747424014337 ") ArrayList<String> serviceIdList,
                                   @RequestParam(required = false) String remark) {
        return rAppointmentService.addAppointment(request, carId, time, serviceIdList, remark);
    }
}

