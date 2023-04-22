package ml.tianhong.rwh.maintain.portal.entity.bo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import ml.tianhong.rwh.maintain.portal.entity.RAppointment;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="AppointmentBO对象", description="拼接后返回前端的appointment对象，不是直接返回carId，而是作为成员对象")
public class AppointmentBO extends RAppointment {
    private CarBO car;
}
