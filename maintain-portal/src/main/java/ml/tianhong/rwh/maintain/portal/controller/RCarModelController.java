package ml.tianhong.rwh.maintain.portal.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ml.tianhong.rwh.maintain.common.api.ResultVO;
import ml.tianhong.rwh.maintain.portal.service.RCarModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rwh
 * @since 2023-03-20
 */
@Api("车辆型号Controller")
@RestController
@RequestMapping("/portal/r-car-model")
public class RCarModelController {

    @Autowired
    private RCarModelService rCarModelService;

    @ApiOperation("新增车辆型号控制器")
    @PostMapping("/addModel")
    public ResultVO addModel(@RequestParam String model, @RequestParam String brand){
        return rCarModelService.addModel(model,brand);
    }

    @ApiOperation("查询车辆型号控制器，前端传入车辆型号id，返回data对象是rCarModel对象的json数据")
    @PostMapping("/getModelById")
    public ResultVO getModelById(@RequestParam String id){
        return rCarModelService.getModelById(id);
    }

    @ApiOperation("获取所有车型控制器")
    @PostMapping("/getModels")
    public ResultVO getModels(){
        return rCarModelService.getModels();
    }

}

