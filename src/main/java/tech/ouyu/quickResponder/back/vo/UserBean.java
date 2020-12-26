package tech.ouyu.quickResponder.back.vo;

import com.sun.istack.internal.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-08 13:52
 * @Description: user对象的传输类
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户传输信息")
public class UserBean {


    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    @NotEmpty(message = "密码不能为空")
    private String password;

    @ApiModelProperty("手机号")
//    @Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$",message = "手机号码格式错误")
    private String phone;

    @ApiModelProperty("记住我")
    private Boolean rememberMe;


    @ApiModelProperty("验证码")
    private String captcha;

 /*   @ApiModelProperty("学号")
    private String studentNumber;*/

    @ApiModelProperty("共用账号属性,前端传送此字段说明用户可能是用户名username、手机号phone或者是学号studentNumber登录")
    private String commonNumber;

    @ApiModelProperty("角色")
    private String roleCode;
}
