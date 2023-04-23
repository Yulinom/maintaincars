package ml.tianhong.rwh.maintain.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import ml.tianhong.rwh.maintain.common.api.ResultVO;
import ml.tianhong.rwh.maintain.common.util.JwtUtils;
import ml.tianhong.rwh.maintain.common.util.TokenCache;
import ml.tianhong.rwh.maintain.portal.entity.ROrder;
import ml.tianhong.rwh.maintain.portal.service.AdminService;
import ml.tianhong.rwh.maintain.portal.service.RAppointmentService;
import ml.tianhong.rwh.maintain.portal.service.RCustomerService;
import ml.tianhong.rwh.maintain.portal.service.ROrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;

@Service
public class AdminServiceImpl implements AdminService {
    @Value("${adminAccount.name}")
    private String adminName;
    @Value("${adminAccount.password}")
    private String adminPwd;
    @Autowired
    private RAppointmentService appointmentService;
    @Autowired
    private ROrderService orderService;
    @Autowired
    private RCustomerService customerService;

    @Override
    public ResultVO login(String adminName, String adminPwd) {
        if (StringUtils.isEmpty(adminName) || StringUtils.isEmpty(adminPwd))
            return ResultVO.error().message("管理员账号或密码不能为空");
        if (adminName.equals(this.adminName) && adminPwd.equals(this.adminPwd)) {
            String token = generateToken();
            return ResultVO.ok().data("adminToken", token);
        } else return ResultVO.error().message("管理员账号或密码错误");
    }

    @Override
    public ResultVO getAdminToken(String adminName, String adminPwd) {
        if (login(adminName, adminPwd).getSuccess()) {
            String token;
            String cacheToken = TokenCache.getKey("admin");
            if (!StringUtils.isEmpty(cacheToken)) token = cacheToken;
            else token = generateToken();

            return ResultVO.ok().data("adminToken", token);
        } else return ResultVO.error();
    }

    @Override
    public ResultVO getAllAppointments() {
        return ResultVO.ok().data("data",appointmentService.list(null));
    }

    @Override
    public ResultVO getAllOrders() {
        return ResultVO.ok().data("data",orderService.list(null));
    }

    @Override
    public ResultVO getAllUsers() {
        return ResultVO.ok().data("data",customerService.list(null));
    }

    @Override
    public ResultVO getDoingOrder() {
        return ResultVO.ok().data("data",orderService.list(new QueryWrapper<ROrder>().eq("status","1")));
    }

    @Override
    public ResultVO addOrder(String appointmentId) {
        return orderService.addOrder(appointmentId);
    }

    @Override
    public ResultVO finishOrder(String orderId, String desc, BigDecimal price) {
        return orderService.finishOrder(orderId,desc,price);
    }

    @Override
    public ResultVO getOrdersByUserId(String userId) {
        //todo 复杂连表查询
        return orderService.getOrderByUserId(userId);
    }

    private String generateToken() {
        String token = JwtUtils.getJwtToken("admin", new Date().toString());
        TokenCache.setLongKey("admin", token);
        return token;
    }

}
