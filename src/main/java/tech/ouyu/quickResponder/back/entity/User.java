package tech.ouyu.quickResponder.back.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-07 15:47
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
@TableName("user")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Builder
public class User extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    Long id;

    @TableField(value = "username", condition = SqlCondition.EQUAL)
    String username;

    @TableField(value = "password")
    String password;

    /**
     * 学号
     *
     * @author ouyu
     */
    @TableField(value = "student_number")
    private String studentNumber;

    /**
     * 头像
     *
     * @author ouyu
     */
    @TableField
    Integer headPicture;

    /**
     * 电话
     *
     * @author ouyu
     */
    @TableField
    private String phone;

    @TableField
    private String salt;

    /**
     * 学分
     *
     * @author ouyu
     */
    @TableField
    private BigDecimal integral;

    /**
     * openId
     * @author ouyu
     */
    @TableField("openId")
    private String openId;

    @TableField
    private String roleCode;

    @TableField(exist = false)
    private Storage storage;

    @TableField(exist = false)
    private ClassGrade classGrade;

    @TableField(exist = false)
    private Role role;
}
