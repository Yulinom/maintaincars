package ml.tianhong.rwh.maintain.portal.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import ml.tianhong.rwh.maintain.common.api.ResultVO;
import ml.tianhong.rwh.maintain.portal.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

@Api("管理页Controller")
@RestController
@RequestMapping("/portal/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    /**
     *
     * @param adminName 管理员名字，可在application.yml文件中配置
     * @param adminPwd 管理员密码
     * @return
     */
    @ApiOperation("管理员登录控制")
    @PostMapping("/login")
    public ResultVO login(@ApiParam("管理员名字，可在application.yml文件中配置") @RequestParam String adminName,
                          @ApiParam("管理员密码")@RequestParam String adminPwd,
                          HttpServletResponse response,
                          HttpSession session){
        ResultVO resultVO = adminService.login(adminName, adminPwd);
        if (resultVO.getSuccess()){
            String token = (String)resultVO.getData().get("adminToken");
            session.setAttribute("admin",token);
            response.addCookie(new Cookie("admin",token));
        }
        return resultVO;
    }

    @ApiOperation("获取管理员操作接口需要携带的token控制")
    @PostMapping("/getAdminToken")
    public ResultVO getAdminToken(@RequestParam String adminName, @RequestParam String adminPwd){
        return adminService.getAdminToken(adminName,adminPwd);
    }

    @ApiOperation("获取所有预约控制")
    @PostMapping("/getAllAppointments")
    public ResultVO getAllAppointments(HttpServletRequest request){
        return adminService.getAllAppointments();
    }

    @ApiOperation("获取所有订单控制")
    @PostMapping("/getAllOrders")
    public ResultVO getAllOrders(HttpServletRequest request){
        return adminService.getAllOrders();
    }

    public ResultVO getOrdersByUserId(HttpServletRequest request, String userId){
        return adminService.getOrdersByUserId(userId);
    }

    @ApiOperation("获取正在进行的订单控制")
    @PostMapping("/getDoingOrder")
    public ResultVO getDoingOrder(HttpServletRequest request){
        return adminService.getDoingOrder();
    }

    @ApiOperation("根据预约生成订单控制")
    @PostMapping("/addOrder")
    public ResultVO addOrder(HttpServletRequest request, @RequestParam String appointmentId){
        return adminService.addOrder(appointmentId);
    }

    @ApiOperation("完成订单")
    @PostMapping("/finishOrder")
    public ResultVO finishOrder(HttpServletRequest request,
                                @ApiParam("订单id")@RequestParam String orderId,
                                @ApiParam("维修描述")@RequestParam String desc,
                                @ApiParam("订单价格")@RequestParam BigDecimal price){
        return adminService.finishOrder(orderId,desc,price);
    }

    @ApiOperation("获取所有用户控制")
    @PostMapping("/getAllUsers")
    public ResultVO getAllUsers(HttpServletRequest request){
        return adminService.getAllUsers();
    }

//    public ResultVO addCustomer()
}
