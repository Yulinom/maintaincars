package ml.tianhong.rwh.maintain.portal.controller;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ml.tianhong.rwh.maintain.common.api.ResultVO;
import ml.tianhong.rwh.maintain.portal.entity.vo.CarVO;
import ml.tianhong.rwh.maintain.portal.service.RCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rwh
 * @since 2023-03-20
 */
@Api("车辆Controller")
@RestController
@RequestMapping("/portal/r-car")
public class RCarController {
    @Autowired
    private RCarService rCarService;

    @ApiOperation("新增车辆控制器，顾客id通过token获取")
    @PostMapping("/addCar")
    public ResultVO addCar(HttpServletRequest request, CarVO car){
        String token = (String) request.getSession().getAttribute("token");
        if (StringUtils.isEmpty(token)) {
            return ResultVO.error().message("请先登录");
        }
        return rCarService.addCar(token,car);
    }

    @ApiOperation("查询用户所有车辆控制器，返回data对象包含了当前用户拥有的车辆列表，且每个车辆对象为CarBO对象")
    @PostMapping("/getCarByToken")
    public ResultVO getCarByToken(HttpServletRequest request){
        return rCarService.getCarsByToken(request);
    }

    @ApiOperation("删除车辆控制器，需要同时校验用户id和车辆id，防止横向越权")
    @PostMapping("/deleteCarById")
    public ResultVO deleteCarById(HttpServletRequest request, @RequestParam String id){
        return rCarService.deleteCarById(request,id);
    }

}

