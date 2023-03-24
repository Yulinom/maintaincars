package ml.tianhong.rwh.maintain.portal.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ml.tianhong.rwh.maintain.common.api.ResultVO;
import ml.tianhong.rwh.maintain.portal.service.RServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rwh
 * @since 2023-03-20
 */
@Api("服务Controller")
@RestController
@RequestMapping("/portal/r-service")
public class RServiceController {
    @Autowired
    private RServiceService rServiceService;

    @ApiOperation("新增服务")
    @PostMapping("/addService")
    public ResultVO addService(String sName){
        return rServiceService.addService(sName);
    }

    @ApiOperation("返回所有能够提供的服务")
    @PostMapping("/getServices")
    public ResultVO getServices(){
        return rServiceService.getServices();
    }
}

