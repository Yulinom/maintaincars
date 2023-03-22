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
@ApiModel(value="RCustomer对象", description="RCustomer对象")
public class RCustomer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "顾客昵称")
    private String name;

    @ApiModelProperty(value = "注册手机号")
    private String phone;

    @ApiModelProperty(value = "用户邮箱")
    private String email;

    @ApiModelProperty(value = "椤惧鍦板潃")
    private String address;

    @ApiModelProperty(value = "账号密码")
    private String password;

    @ApiModelProperty(value = "是否禁用，0表示启用，1表示禁用，默认0")
    private String disable;

    @ApiModelProperty(value = "逻辑删除位，0表示有效，1表示已删除，默认0")
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private String deleted;

    @ApiModelProperty(value = "创建时间戳")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间戳")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtUpdate;


}
