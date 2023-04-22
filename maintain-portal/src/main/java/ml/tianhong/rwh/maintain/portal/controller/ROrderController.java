package ml.tianhong.rwh.maintain.portal.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ml.tianhong.rwh.maintain.common.api.ResultVO;
import ml.tianhong.rwh.maintain.portal.service.ROrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author rwh
 * @since 2023-03-20
 */
@RestController
@RequestMapping("/portal/r-order")
@Api("订单Controller")
public class ROrderController {

    @Autowired
    private ROrderService orderService;

    @ApiOperation("新增订单控制器，传入预约id，根据预约生成订单，一条预约生成一条订单")
    @PostMapping("/addOrder")
    public ResultVO addOrder(HttpServletRequest request, @RequestParam String appointmentId){
        return orderService.addOrder(request, appointmentId);
    }

    @ApiOperation("根据用户token获取该用户所有订单，返回的data对象为订单列表的json格式")
    @PostMapping("/getOrdersByToken")
    public ResultVO getOrdersByToken(HttpServletRequest request){
        return orderService.getOrdersByToken(request);
    }

    @ApiOperation("完成订单控制器，传入该订单id，维修详情和订单价格")
    @PutMapping("/finishOrder")
    public ResultVO finishOrder(String orderId, String desc, BigDecimal price){
        return orderService.finishOrder(orderId, desc, price);
    }


}

