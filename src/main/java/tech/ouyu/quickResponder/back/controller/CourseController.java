package tech.ouyu.quickResponder.back.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tech.ouyu.quickResponder.back.service.SubjectSchoolTimeService;
import tech.ouyu.quickResponder.back.service.UserSubjectService;
import tech.ouyu.quickResponder.back.vo.AuthBean;
import tech.ouyu.quickResponder.back.vo.Result;
import tech.ouyu.quickResponder.back.vo.TeacherCouser;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-20 16:33
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
@Api("课程")
@RestController
@RequestMapping("/course")
@AllArgsConstructor
public class CourseController {

    final UserSubjectService userSubjectService;
    final SubjectSchoolTimeService subjectSchoolTimeService;

    @ApiOperation("同学添加课程 ")
    @PostMapping("/addCourse")
    public Result addCourse(@RequestAttribute Long userId, Long courseId) {
        return new Result<AuthBean>().success(userSubjectService.addCourse(userId, courseId));
    }

    @ApiOperation("老师添加课程")
    @PostMapping("/teacherAddCourse")
    public Result teacherAddCourse(@RequestBody TeacherCouser teacherCouser) {
        return new Result<Boolean>().success(subjectSchoolTimeService.teacherAddCourse(teacherCouser));
    }
}
