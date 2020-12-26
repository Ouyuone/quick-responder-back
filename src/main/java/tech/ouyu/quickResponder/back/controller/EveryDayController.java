package tech.ouyu.quickResponder.back.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.ouyu.quickResponder.back.entity.Everyday;
import tech.ouyu.quickResponder.back.service.EveryDayService;
import tech.ouyu.quickResponder.back.vo.Result;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-11 11:19
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
@Api(value = "每日需要做的事")
@RestController
@RequestMapping("/everyday")
@AllArgsConstructor
public class EveryDayController {
    final EveryDayService everyDayService;

    @ApiOperation(value = "每日一句")
    @GetMapping("/oneword")
    public Result oneword(){
        return new Result<Everyday>().success(everyDayService.readOneword());
    }
}
