package tech.ouyu.quickResponder.back.util;

import lombok.extern.slf4j.Slf4j;
import tech.ouyu.quickResponder.back.constant.DescribeConstant;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-08 11:01
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
@Slf4j
public class UserIdThreadLocal {
    private static ThreadLocal<Long> userIdThreadLocal = new ThreadLocal<Long>() {
        @Override
        protected Long initialValue() {
            return -1L;
        }
    };

    public static Long getUserId() {
        if (userIdThreadLocal.get() == -1) {
            log.error(DescribeConstant.NO_USERID);
        } else if (userIdThreadLocal.get() == -2) {
            log.error(DescribeConstant.USERID_EMPTY);
            throw new IllegalArgumentException(DescribeConstant.USERID_EMPTY);
        }
        return userIdThreadLocal.get();
    }

    public static void serUserId(Long userId) {
        if (userId == null || userId == 0) {
            log.error(DescribeConstant.USERID_EMPTY);
            userId = -2L;
        }
        userIdThreadLocal.set(userId);
    }

    public static void removeUserId(){
        userIdThreadLocal.remove();;
    }
}
