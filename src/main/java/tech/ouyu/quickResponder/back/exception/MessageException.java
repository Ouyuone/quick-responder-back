package tech.ouyu.quickResponder.back.exception;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-08 11:12
 * @Description: 消息异常类
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
public class MessageException extends RuntimeException {
    private int code;

    public MessageException() {
        super();
    }

    public MessageException(String message) {
        super(message);
    }

    public MessageException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
