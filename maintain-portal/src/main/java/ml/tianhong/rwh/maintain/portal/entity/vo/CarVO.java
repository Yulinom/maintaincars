package ml.tianhong.rwh.maintain.portal.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="CarVO对象", description="接收前端传入的car对象")
public class CarVO {
    @ApiModelProperty(value = "车牌号")
    private String registrationNumber;

    @ApiModelProperty(value = "车属地")
    private String licenseSpace;

    @ApiModelProperty(value = "车型号")
    private String modelId;

    @ApiModelProperty(value = "车辆所属顾客id")
    private String customerId;
}
