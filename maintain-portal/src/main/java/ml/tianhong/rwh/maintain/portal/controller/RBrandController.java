package ml.tianhong.rwh.maintain.portal.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ml.tianhong.rwh.common.api.ResultVO;
import ml.tianhong.rwh.maintain.portal.service.RBrandService;
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
@Api("车辆品牌Controller")
@RestController
@RequestMapping("/portal/r-brand")
public class RBrandController {

    @Autowired
    private RBrandService rBrandService;

    @ApiOperation("新增品牌控制器，传入品牌名字")
    @PostMapping("/addBrand")
    public ResultVO addBrand(@RequestParam String brand){
        return rBrandService.addBrand(brand);
    }

    @ApiOperation("根据名字查询品牌控制器，传入品牌名字，返回data对象是一个包含品牌对象的列表")
    @PostMapping("/getBrandByName")
    public ResultVO getBrandByName(@RequestParam String brand){
        return rBrandService.getBrandByName(brand);
    }
}

