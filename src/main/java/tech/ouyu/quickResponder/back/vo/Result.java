package tech.ouyu.quickResponder.back.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-08 13:49
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
@Data
@ApiModel("响应信息")
@Accessors(chain = true)
public class Result<T> {
    private int code;
    private String message;
    private T data;

    public Result() {
    }

    public Result(T data) {
        this(0, (String) null);
        this.data = data;
    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public Result<T> success(int code, T data){
        return new Result<>(code,data);
    }

    public Result<T> success(T data){
        return new Result<>(data);
    }
    public Result<T> success(String message,T data){
        return new Result<T>(0,message,data);
    }

    public Result validError(BindingResult result) {
        List<FieldError> errors = result.getFieldErrors();
        FieldError fieldError = (FieldError)errors.get(0);
        return this.error(fieldError.getDefaultMessage());
    }

    public Result error(String message){
       return new Result(999,message,null);
    }
    public Result error(int code, String message){
        return new Result(code,message,null);
    }
}
