package tech.ouyu.quickResponder.back.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-29 16:28
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
@Data
public class TeacherCouser {
    private String subject;
    private Long gradeId;
    private Date overdueTime;
    private List<SchoolTimeVo> weeks;
}
