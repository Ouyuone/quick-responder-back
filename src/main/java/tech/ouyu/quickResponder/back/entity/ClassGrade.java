package tech.ouyu.quickResponder.back.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * generated by Generate POJOs.groovy
 * <p>Date: Wed Dec 09 10:47:55 CST 2020.</p>
 *
 * @author (ousakai)
 */

@Data
@TableName(value = "class_grade")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassGrade {


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 班级名
     */
    @TableField(value = "class_grade_name")
    private String classGradeName;

    @TableField(value = "is_del")
    private Integer isDel;

}
