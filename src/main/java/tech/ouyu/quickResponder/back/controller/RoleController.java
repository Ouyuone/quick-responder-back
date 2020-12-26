package tech.ouyu.quickResponder.back.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.ouyu.quickResponder.back.constant.DescribeConstant;
import tech.ouyu.quickResponder.back.entity.Role;
import tech.ouyu.quickResponder.back.service.RoleService;
import tech.ouyu.quickResponder.back.vo.Result;

import javax.management.relation.RoleInfo;
import java.util.List;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-17 14:14
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
@Api("角色Api")
@RequestMapping("/role")
@RestController
@AllArgsConstructor
public class RoleController {
    final RoleService roleService;

    @GetMapping("/roles")
    @ApiOperation(value = "获取全部角色信息")
    @ApiImplicitParam(name = DescribeConstant.Header.X_CURRENT_TOKEN,value = "Token令牌",required = false,dataType = "String",paramType = "header")
    public Result roles(){
        return new Result<List<Role>>().success(roleService.roles());
    }
}
