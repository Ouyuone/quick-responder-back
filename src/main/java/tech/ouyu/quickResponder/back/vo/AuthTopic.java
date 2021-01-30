package tech.ouyu.quickResponder.back.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2021-01-28 16:45
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
@Data
@ApiModel("在线题库")
public class AuthTopic {
    @ApiModelProperty("权限标题")
    private String title;
    @ApiModelProperty("班级id")
    private Long classGradeId;
    @ApiModelProperty("角色")
    private String roleCode;
}
