package tech.ouyu.quickResponder.back.controller;

import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tech.ouyu.quickResponder.back.constant.DescribeConstant;
import tech.ouyu.quickResponder.back.service.UserService;
import tech.ouyu.quickResponder.back.vo.MpUserInfo;
import tech.ouyu.quickResponder.back.vo.Result;
import tech.ouyu.quickResponder.back.vo.UserBean;
import tech.ouyu.quickResponder.back.vo.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-08 13:46
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
@Api(value = "用户服务")
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    final UserService userService;

    @ApiOperation(value = "登录服务", notes = "登录验证，通过返回token")
    @ApiImplicitParam(name = DescribeConstant.Header.X_CURRENT_TOKEN, value = "token令牌", required = false, dataType = "String",paramType="header")
    @PostMapping("/doLogin")
    public Result doLogin(@Valid @RequestBody UserBean userBean, BindingResult errors) {
        if (errors.hasErrors()) {
            return new Result<String>().validError(errors);
        }
        return new Result<String>().success("登录成功,请享受你的权益",userService.doLogin(userBean));
    }

    @ApiOperation(value = "使用UserId登录服务", notes = "登录验证，通过返回token")
    @ApiImplicitParam(name = DescribeConstant.Header.X_CURRENT_TOKEN, value = "token令牌", required = false, dataType = "String",paramType="header")
    @PostMapping("/doLoginUserId")
    public Result doLoginUserId(Long userId) {
        return new Result<String>().success("登录成功,请享受你的权益",userService.doLoginUserId(userId));
    }

    @ApiOperation(value = "注册服务")
    @ApiImplicitParam(name = DescribeConstant.Header.X_CURRENT_TOKEN, value = "token令牌", required = false, dataType = "String",paramType="header")
    @PostMapping("/doRegister")
    public Result doRegister(@Valid @RequestBody UserBean userBean, BindingResult errors) {
        if (errors.hasErrors()) {
            return new Result<String>().validError(errors);
        }
        return new Result<String>().success("注册成功",userService.doRegister(userBean));
    }

    @ApiOperation(value = "获取用户信息")
    @GetMapping("/userInfo")
    @ApiImplicitParam(name = "userId",value = "用户ID",dataType = "Long",paramType = "query",example = "0")
    public Result userInfo(@RequestAttribute  Long userId) {
        return new Result<UserInfo>().success(userService.userInfo(userId));
    }

    @ApiOperation("小程序使用openid获取用户信息且进行注册")
    @PostMapping("/UserInfoByOpenIdOrRegister")
    @ApiImplicitParam(name = DescribeConstant.Header.X_CURRENT_TOKEN, value = "token令牌", required = false, dataType = "String",paramType="header")
    public Result UserInfoByOpenIdOrRegister(@RequestBody UserInfo userInfo){
            return new Result<MpUserInfo>().success(userService.UserInfoByOpenIdOrRegister(userInfo));
    }

}
