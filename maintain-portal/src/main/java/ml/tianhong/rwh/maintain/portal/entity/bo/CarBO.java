package ml.tianhong.rwh.maintain.portal.entity.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import ml.tianhong.rwh.maintain.portal.entity.RCar;
import ml.tianhong.rwh.maintain.portal.entity.RCarModel;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="CarBO对象", description="拼接后返回前端的car对象，不是直接返回车型id，而是作为成员对象")
public class CarBO {
    @ApiModelProperty(value = "车牌号")
    private String registrationNumber;

    @ApiModelProperty(value = "车属地")
    private String licenseSpace;

    @ApiModelProperty(value = "车型对象")
    private RCarModel model;

    @ApiModelProperty(value = "车辆所属顾客id")
    private String customerId;

    public CarBO(RCar rCar){
        this.customerId=rCar.getCustomerId();
        this.licenseSpace=rCar.getLicenseSpace();
        this.registrationNumber=rCar.getRegistrationNumber();
    }
}
