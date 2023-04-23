package ml.tianhong.rwh.maintain.portal.service;

import ml.tianhong.rwh.maintain.common.api.ResultVO;
import ml.tianhong.rwh.maintain.portal.entity.ROrder;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author rwh
 * @since 2023-03-20
 */
public interface ROrderService extends IService<ROrder> {

    /**
     * 根据预约生成订单，并且把当前预约status设为2-已完成，当前订单status设为1-正在服务,开始时间获取当前时间，结束时间（同开始时间）和价格（-1）暂未生效，但不能为空
     * @param request
     * @param appointmentId
     * @return
     */
    ResultVO addOrder(HttpServletRequest request, String appointmentId);

    ResultVO getOrdersByToken(HttpServletRequest request);

    ResultVO getOrderByUserId(String userId);

    //todo 应该是商家管理的操作
    ResultVO finishOrder(String orderId, String desc, BigDecimal price);

    /**
     * 商家管理添加订单
     * 根据预约生成订单，并且把当前预约status设为2-已完成，当前订单status设为1-正在服务,开始时间获取当前时间，结束时间（同开始时间）和价格（-1）暂未生效，但不能为空
     * @param appointmentId
     * @return
     */
    ResultVO addOrder(String appointmentId);
}
