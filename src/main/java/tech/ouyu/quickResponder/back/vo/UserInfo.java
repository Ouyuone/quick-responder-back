package tech.ouyu.quickResponder.back.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-09 08:47
 * @Description:
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
@ApiModel("用户信息")
public class UserInfo {
    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("名字")
    private String username;

    @ApiModelProperty("头像")
    private String headPicture;

    @ApiModelProperty("班级名")
    private String classGradeName;

    @ApiModelProperty("班级id")
    private Long classGradeId;

    @ApiModelProperty("学号")
    private String studentNumber;

    @ApiModelProperty("学分")
    private BigDecimal integral;

    @ApiModelProperty("openId")
    private String openId;

    @ApiModelProperty("角色code")
    private String roleCode;

    @ApiModelProperty("角色名")
    private String roleName;
}
