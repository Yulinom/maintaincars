package ml.tianhong.rwh.maintain.portal.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="注册传入User对象", description="注册传入User对象")
public class UserVO {
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
}
