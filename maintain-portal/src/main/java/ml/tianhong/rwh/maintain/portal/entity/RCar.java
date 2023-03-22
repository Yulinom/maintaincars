package ml.tianhong.rwh.maintain.portal.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author rwh
 * @since 2023-03-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="RCar对象", description="")
public class RCar implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "车牌号")
    private String registrationNumber;

    @ApiModelProperty(value = "车属地")
    private String licenseSpace;

    @ApiModelProperty(value = "车型号")
    private String modelId;

    @ApiModelProperty(value = "车辆所属顾客id")
    private String customerId;

    @ApiModelProperty(value = "逻辑删除")
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private String deleted;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtUpdate;


}
