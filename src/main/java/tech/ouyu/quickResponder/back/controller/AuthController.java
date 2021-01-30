package tech.ouyu.quickResponder.back.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tech.ouyu.quickResponder.back.service.AuthService;
import tech.ouyu.quickResponder.back.vo.AuthBean;
import tech.ouyu.quickResponder.back.vo.AuthTopic;
import tech.ouyu.quickResponder.back.vo.Result;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-17 17:15
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
@Api("权限表")
@RequestMapping("/authAccess")
@RestController
@AllArgsConstructor
public class AuthController {

    final AuthService authService;
    @ApiOperation(value = "获取课程")
    @GetMapping("/course")
    public Result<AuthBean> course(@RequestAttribute Long userId,String accessUrl,@RequestParam(required = false) Long classGradeId){
        return new Result<AuthBean>().success(authService.course(userId,accessUrl,classGradeId));
    }

    @ApiOperation(value = "获取题库")
    @GetMapping("/liveTopic")
    public Result liveTopic(@RequestAttribute Long userId, String accseeUrl){
        return new Result<AuthTopic>().success(authService.liveTopic(userId,accseeUrl));
    }

}
