package ml.tianhong.rwh.maintain.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import ml.tianhong.rwh.maintain.common.api.ResultVO;
import ml.tianhong.rwh.maintain.portal.entity.RAppointment;
import ml.tianhong.rwh.maintain.portal.entity.ROrder;
import ml.tianhong.rwh.maintain.portal.mapper.ROrderMapper;
import ml.tianhong.rwh.maintain.portal.service.RAppointmentService;
import ml.tianhong.rwh.maintain.portal.service.ROrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author rwh
 * @since 2023-03-20
 */
@Service
public class ROrderServiceImpl extends ServiceImpl<ROrderMapper, ROrder> implements ROrderService {

    @Autowired
    private RAppointmentService appointmentService;

    @Override
    public ResultVO addOrder(HttpServletRequest request, String appointmentId) {
        ResultVO appointmentData = appointmentService.getAppointmentByAppointmentId(request, appointmentId);
        RAppointment appointment = (RAppointment) appointmentData.getData().get("data");
        if (appointmentData.getSuccess()) {
            appointmentService.updateById(appointment.setStatus("2"));
            Date date = new Date();
            ROrder order = new ROrder()
                    .setStartTime(date)
                    .setFinishTime(date)
                    .setPrice(new BigDecimal(-1))
                    .setStatus("1")
                    .setAppointmentId(appointment.getId());
            baseMapper.insert(order);
            return ResultVO.ok();
        }
        return ResultVO.error();
    }

    @Override
    public ResultVO getOrdersByToken(HttpServletRequest request) {
        ResultVO appointmentsData = appointmentService.getAppointments(request);
        if (appointmentsData.getSuccess()) {
            ArrayList<RAppointment> appointments = (ArrayList<RAppointment>) appointmentsData.getData().get("data");
            if (appointments.isEmpty()) return ResultVO.ok();
            else {
                ArrayList<ROrder> rOrders = new ArrayList<>();
                for (RAppointment appointment : appointments) {
                    ROrder order = baseMapper.selectOne(new QueryWrapper<ROrder>().eq("appointment_id", appointment.getId()));
                    rOrders.add(order);
                }

                return ResultVO.ok().data("data", rOrders);
            }
        }
        return appointmentsData;
    }

    @Override
    public ResultVO finishOrder(String orderId, String desc, BigDecimal price) {
        if (StringUtils.isEmpty(orderId) || price == null)
            return ResultVO.error().message("非法请求");
        else {
            QueryWrapper<ROrder> queryWrapper = new QueryWrapper<ROrder>().eq("id", orderId);
            baseMapper.update(new ROrder().setPrice(price).setDescription(desc).setFinishTime(new Date()).setStatus("2"),queryWrapper);
            return ResultVO.ok();
        }
    }

    @Override
    public ResultVO addOrder(String appointmentId) {
        RAppointment appointment = appointmentService.getOne(new QueryWrapper<RAppointment>().eq("id", appointmentId));
        if (appointment == null) {
            return ResultVO.error().message("添加为订单的预约不存在");
        }
        appointmentService.updateById(appointment.setStatus("2"));
        Date date = new Date();
        ROrder order = new ROrder()
                .setStartTime(date)
                .setFinishTime(date)
                .setPrice(new BigDecimal(-1))
                .setStatus("1")
                .setAppointmentId(appointment.getId());
        baseMapper.insert(order);
        return ResultVO.ok();
    }

}
