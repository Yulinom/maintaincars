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
@ApiModel(value="RAppointment对象", description="")
public class RAppointment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    private String carId;

    @ApiModelProperty(value = "预约状态，0表示预约失败，1表示预约成功")
    private String status;

    @ApiModelProperty(value = "选择预约日期")
    private Date dateRecord;

    @ApiModelProperty(value = "选择预约时间段")
    private String timeId;

    @ApiModelProperty(value = "维修信息描述，设计存储富文本")
    private String remark;

    @ApiModelProperty(value = "逻辑删除")
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private String deleted;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtUpdate;


}
