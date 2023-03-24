package ml.tianhong.rwh.maintain.portal.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ml.tianhong.rwh.maintain.common.api.ResultVO;
import ml.tianhong.rwh.maintain.portal.entity.RTimeSet;
import ml.tianhong.rwh.maintain.portal.service.RTimeSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.crypto.Data;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rwh
 * @since 2023-03-24
 */
@Api("预约时间段Controller")
@RestController
@RequestMapping("/portal/r-time-set")
public class RTimeSetController {

    @Autowired
    private RTimeSetService timeSetService;

    @PostMapping("/addTimeSet")
    public ResultVO addTimeSet(Date startTime, Date endTime){
        return timeSetService.addTimeSet(startTime,endTime);
    }

    @ApiOperation("获取所有服务时间段")
    @PostMapping("/getTimeSets")
    public ResultVO getTimeSets(){
        return timeSetService.getTimeSets();
    }

    @PostMapping("/getTimeSetById")
    public ResultVO getTimeSetById(@RequestParam String id){
        return timeSetService.getTimeSetById(id);
    }
}

