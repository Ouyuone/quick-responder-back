package tech.ouyu.quickResponder.back.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import tech.ouyu.quickResponder.back.entity.ClassGrade;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-17 17:24
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
@Data
@Builder
@ApiModel("授权访问类")
public class AuthBean {
    @ApiModelProperty("权限标题")
    private String title;
    @ApiModelProperty("班级id")
    private Long classGradeId;
    @ApiModelProperty("班级名")
    private String classGradeName;
    @ApiModelProperty("角色")
    private String roleCode;
    @ApiModelProperty("学科信息")
    private List<Courses> courses;
    @ApiModelProperty("老师会有多个班级")
    private List<ClassGrade> classGrades;

    @AllArgsConstructor
    @Data
    private class Courses {
        @ApiModelProperty("学科ID")
        private Long subjectId;
        @ApiModelProperty("学科")
        private String subject;
        @ApiModelProperty("班级")
        private String grade;
        @ApiModelProperty("上课提示语")
        private String schoolTimeDesc;
        @ApiModelProperty("是否上课标记")
        private boolean schoolTimeMark;
        @ApiModelProperty("星期几")
        private Integer week;
        @ApiModelProperty("每日上课时间")
        private String schoolTime;
    }

    public void addCourses(Long subjectId, String subject, String grade, String schoolTimeDesc, boolean schoolTimeMark, Integer week, String schoolTime) {
        if (courses == null) {
            courses = new ArrayList<>();
        }
        this.courses.add(new Courses(subjectId, subject, grade, schoolTimeDesc, schoolTimeMark, week, schoolTime));
    }

    private void removeCoursesALl() throws IllegalAccessException {
        if (courses == null) {
            throw new IllegalAccessException("未初始化AuthBean.Course");
        }
        this.courses.clear();
    }
}

