package tech.ouyu.quickResponder.back.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tech.ouyu.quickResponder.back.exception.MessageException;
import tech.ouyu.quickResponder.back.vo.Result;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-10 09:45
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
@Slf4j
@RestControllerAdvice(basePackages = "tech.ouyu.quickResponder.back.controller")
public class ControllerExceptionHandler {

    @ExceptionHandler
    public Result messageExceptionHandler(MessageException e){
        log.error("检查异常:",e);
        return new Result<String>().error(e.getMessage());
    }


    @ExceptionHandler
    public Result exceptionHandler(Exception e){
        log.error("运行时异常:",e);
       return new Result<String>().error(e.getMessage());
    }
}
