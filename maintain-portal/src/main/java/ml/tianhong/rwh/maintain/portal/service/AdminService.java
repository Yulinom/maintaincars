package ml.tianhong.rwh.maintain.portal.service;

import ml.tianhong.rwh.maintain.common.api.ResultVO;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public interface AdminService {
    ResultVO login(String adminName, String adminPwd);
    ResultVO getAdminToken(String adminName, String adminPwd);

    ResultVO getAllAppointments();

    ResultVO getAllOrders();

    ResultVO getAllUsers();

    ResultVO getDoingOrder();

    ResultVO addOrder(String appointmentId);

    ResultVO finishOrder(String orderId, String desc, BigDecimal price);

    ResultVO getOrdersByUserId(String userId);
}
