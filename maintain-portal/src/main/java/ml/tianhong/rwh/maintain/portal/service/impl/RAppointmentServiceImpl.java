package ml.tianhong.rwh.maintain.portal.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ml.tianhong.rwh.common.api.ResultVO;
import ml.tianhong.rwh.common.util.MyException;
import ml.tianhong.rwh.maintain.portal.entity.RAppointment;
import ml.tianhong.rwh.maintain.portal.mapper.RAppointmentMapper;
import ml.tianhong.rwh.maintain.portal.service.RAppointmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ml.tianhong.rwh.maintain.portal.service.RCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;
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
public class RAppointmentServiceImpl extends ServiceImpl<RAppointmentMapper, RAppointment> implements RAppointmentService {

    @Autowired
    private RCarService rCarService;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public ResultVO addAppointment(HttpServletRequest request, String carId, Date time, String serviceIdList, String remark) {
        if (StringUtils.isEmpty(serviceIdList)
                || StringUtils.isEmpty((String) request.getSession().getAttribute("token"))
                || StringUtils.isEmpty(carId)
                || time == null)
            return ResultVO.error().message("非法请求");


        if (rCarService.getCarById(carId, request).getSuccess()) {
            //先添加预约记录，拿到新生成的预约id
            RAppointment rAppointment = new RAppointment();

            rAppointment.setCarId(carId).setStatus("0").setTime(time).setRemark(remark);
            baseMapper.insert(rAppointment);
            System.out.println(rAppointment);

            //添加预约和服务的多对多关系记录
            try {
                ArrayList list = objectMapper.readValue(serviceIdList, ArrayList.class);
                for (Object o : list) {

                }

            } catch (JsonProcessingException e) {
                throw new MyException(20001,"服务对象json反序列化为对象失败");
            }
        }

        return ResultVO.error();
    }
}
