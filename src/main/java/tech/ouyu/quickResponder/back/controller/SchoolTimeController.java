package tech.ouyu.quickResponder.back.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.ouyu.quickResponder.back.service.SchoolTimeService;
import tech.ouyu.quickResponder.back.vo.Result;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-21 14:18
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
@Api("上课时间表")
@RestController
@RequestMapping("/schoolTime")
@AllArgsConstructor
public class SchoolTimeController {
    final SchoolTimeService schoolTimeService;
    @GetMapping("/findByFreeTime")
    @ApiOperation("寻找本班级空闲上课时间")
    public Result findByFreeTime(@RequestAttribute Long userId,Long classGradeId) {
            return new Result().success(schoolTimeService.findByFreeTime(userId,classGradeId));
    }
}
