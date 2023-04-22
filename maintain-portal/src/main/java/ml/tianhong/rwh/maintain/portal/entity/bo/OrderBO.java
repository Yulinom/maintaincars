package ml.tianhong.rwh.maintain.portal.entity.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import ml.tianhong.rwh.maintain.portal.entity.RAppointment;
import ml.tianhong.rwh.maintain.portal.entity.ROrder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "OrderBO对象", description = "后端拼接后返回前端的order对象")
public class OrderBO implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "完成时间")
    private Date finishTime;

    @ApiModelProperty(value = "状态，0-未开始，1-正在维修，2-已完成")
    private String status;

    @ApiModelProperty(value = "详情")
    private String desc;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "预约")
    private RAppointment rAppointment;

    public OrderBO(ROrder rOrder) {
        this.startTime=rOrder.getStartTime();
        this.finishTime=rOrder.getFinishTime();
        this.desc=rOrder.getDescription();
        this.price=rOrder.getPrice();
        this.status=rOrder.getStatus();
    }

    public OrderBO() {
    }
}
