package tech.ouyu.quickResponder.back.dao;

import lombok.Data;
import tech.ouyu.quickResponder.back.entity.ClassGrade;
import tech.ouyu.quickResponder.back.entity.Subject;

import java.util.List;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-17 22:13
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
@Data
public class UserSourse {
    private Long id;
    private List<ClassGrade> classGrade;
    private List<Subject> subject;
}
