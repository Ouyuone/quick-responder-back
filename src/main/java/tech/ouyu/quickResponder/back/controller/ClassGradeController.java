package tech.ouyu.quickResponder.back.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tech.ouyu.quickResponder.back.entity.ClassGrade;
import tech.ouyu.quickResponder.back.service.ClassGradeService;
import tech.ouyu.quickResponder.back.vo.ClassGradeVo;
import tech.ouyu.quickResponder.back.vo.Result;

import java.util.List;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2021-01-05 10:34
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
@Api("班级")
@RestController
@RequestMapping("/classGrade")
@AllArgsConstructor
public class ClassGradeController {
    final ClassGradeService classGradeService;

    @ApiOperation("获取我的班级")
    @GetMapping("/getMyGrade")
    public Result getMyGrade(@RequestAttribute Long userId){
        return new Result<List<ClassGradeVo>>().success(classGradeService.getMyGrade(userId));
    }

    @ApiOperation("获取所有班级")
    @GetMapping("/getAllGrade")
    public Result getAllGrade(@RequestAttribute Long userId) {
        return new Result<List<ClassGrade>>().success(classGradeService.getAllGrade(userId));
    }

    @ApiOperation("添加班级")
    @PostMapping("/doAddClassGrade")
    public Result doAddClassGrade(@RequestAttribute Long userId,@RequestParam Long classGradeId){
        return new Result<List<ClassGradeVo>>().success(classGradeService.doAddClassGrade(userId,classGradeId));
    }

    @ApiOperation("老师新增班级")
    @PostMapping("/addNewClassGrade")
    public Result addNewClassGrade(@RequestParam String classGradeName){
        return new Result<Boolean>().success(classGradeService.addNewClassGrade(classGradeName));
    }
}
