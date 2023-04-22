package ml.tianhong.rwh.maintain.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import ml.tianhong.rwh.maintain.common.api.ResultVO;
import ml.tianhong.rwh.maintain.common.util.JwtUtils;
import ml.tianhong.rwh.maintain.portal.entity.RAppointment;
import ml.tianhong.rwh.maintain.portal.entity.RCar;
import ml.tianhong.rwh.maintain.portal.entity.bo.CarBO;
import ml.tianhong.rwh.maintain.portal.mapper.RAppointmentMapper;
import ml.tianhong.rwh.maintain.portal.service.RAppointmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ml.tianhong.rwh.maintain.portal.service.RAsService;
import ml.tianhong.rwh.maintain.portal.service.RCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author rwh
 * @since 2023-03-20
 */
@Service
public class RAppointmentServiceImpl extends ServiceImpl<RAppointmentMapper, RAppointment> implements RAppointmentService {

    @Autowired
    private RCarService rCarService;

    @Autowired
    private RAsService rAsService;

    @Override
    public ResultVO addAppointment(HttpServletRequest request, String carId, Date dateRecord, String timeId, ArrayList<String> serviceIdList, String remark) {
        if (serviceIdList == null
                || StringUtils.isEmpty((String) request.getSession().getAttribute("token"))
                || StringUtils.isEmpty(carId)
                || dateRecord == null
                || StringUtils.isEmpty(timeId)
        )
            return ResultVO.error().message("非法请求");


        if (rCarService.getCarById(carId, request).getSuccess()) {
            //先添加预约记录，拿到新生成的预约id
            RAppointment rAppointment = new RAppointment();

            rAppointment.setCarId(carId).setStatus("0").setDateRecord(dateRecord).setTimeId(timeId).setRemark(remark);
            baseMapper.insert(rAppointment);
            System.out.println(rAppointment);

            //添加预约和服务的多对多关系记录
            for (Object o : serviceIdList) {
                rAsService.addRAs(rAppointment.getId(), (String) o);
            }

            return ResultVO.ok();
        }

        return ResultVO.error();
    }

    public ResultVO addAppointment(HttpServletRequest request, String carId, Date dateRecord, String timeId, ArrayList<String> serviceIdList, String remark, String phone, String registrationNumber) {
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(registrationNumber)) {
            return ResultVO.error().message("非法请求");
        }
        remark += "\t\t***临时用户的电话为：" + phone + "\n\t\t***车牌号为：" + registrationNumber;

        //向request的session中注入token
//        request.getSession().setAttribute("token", JwtUtils.getJwtToken("admin"));

        return addAppointment(request, carId, dateRecord, timeId, serviceIdList, remark);
    }

    @Override
    public ResultVO getAppointments(HttpServletRequest request) {
        ResultVO cars = rCarService.getCarsByToken(request);
        //todo 根据用户id查找carId再查appointment，实际应该联表查询，这里写臃肿点
        if (cars.getSuccess()) {
            Map<String, Object> carsData = cars.getData();
            ArrayList<CarBO> carsList = (ArrayList<CarBO>) carsData.get("data");
            LinkedList<RAppointment> linkedList = new LinkedList<>();
            for (CarBO carBO : carsList) {
                ArrayList<RAppointment> arrayList = (ArrayList<RAppointment>)getAppointmentsByCarId(request, carBO.getCarId()).getData().get("data");
                linkedList.addAll(arrayList);
            }
            return ResultVO.ok().data("data",linkedList);
        } else return cars;
    }

    @Override
    public ResultVO getAppointmentsByCarId(HttpServletRequest request, String carId) {
        String token = (String) request.getSession().getAttribute("token");
        if ((!StringUtils.isEmpty(carId)) && (!StringUtils.isEmpty(token))) {
            List<RAppointment> appointments = baseMapper.selectList(new QueryWrapper<RAppointment>().eq("car_id", carId));
            return ResultVO.ok().data("data",appointments);
        }

        return ResultVO.error().message("非法请求");
    }

    public ResultVO deleteAppointment(HttpServletRequest request, String appointmentId) {
        if (StringUtils.isEmpty(appointmentId)) return ResultVO.error().message("非法请求");
        RAppointment appointment = baseMapper.selectOne(new QueryWrapper<RAppointment>().eq("id", appointmentId));
        ResultVO car = rCarService.getCarById(appointment.getCarId(), request);
        if (car.getSuccess()) {
            baseMapper.deleteById(appointment);
            return ResultVO.ok();
        } else return car;
    }

    @Override
    public ResultVO getAppointmentByAppointmentId(HttpServletRequest request, String appointmentId) {
        if (StringUtils.isEmpty(appointmentId)) return ResultVO.error().message("非法请求");
        RAppointment appointment = baseMapper.selectOne(new QueryWrapper<RAppointment>().eq("id", appointmentId));
        if (appointment == null) {
            return ResultVO.error().message("预约不存在");
        }else {
            if (rCarService.getCarById(appointment.getCarId(),request).getSuccess()) {
                return ResultVO.ok().data("data",appointment);
            }
        }
        return ResultVO.error().message("非法请求");
    }

}
