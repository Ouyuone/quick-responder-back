package tech.ouyu.quickResponder.back.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.ouyu.quickResponder.back.constant.DescribeConstant;
import tech.ouyu.quickResponder.back.service.impl.DocumentService;
import tech.ouyu.quickResponder.back.vo.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-13 10:25
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
@Api(value = "文档")
@RestController
@RequestMapping("/document")
@AllArgsConstructor
public class DocumentController {
    final DocumentService documentService;

    @GetMapping("/captcha")
    @ApiOperation(value = "获取验证码")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = DescribeConstant.Header.X_CURRENT_TOKEN, value = "token令牌", required = false, dataType = "String",paramType="header"),
            @ApiImplicitParam(name = "type", value = "验证码类型", required = true, dataType = "String",paramType="query")
    })
    public Result validcode(HttpServletRequest request, HttpServletResponse response){
        return new Result<String>().success(documentService.getCaptchaImage(request,response));
    }
}
