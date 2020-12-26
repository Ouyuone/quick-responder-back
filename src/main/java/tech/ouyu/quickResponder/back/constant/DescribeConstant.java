package tech.ouyu.quickResponder.back.constant;

import java.util.Date;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-08 11:05
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
public interface DescribeConstant {

     String NO_USERID = "没有用户ID,可能是系统操作";
     String USERID_EMPTY = "用户ID不能为空或0";

     interface Header{
         String X_CURRENT_TOKEN = "X-CURRENT-TOKEN";
     }

     interface RedisKeys{
         String QR_STUDENT_NUMBER = "qr_student_number";
         String QR_STUDENT_NUMBER_COUNT = "qr_student_number_count";
     }

     interface Role{
         String QR_STUDENT ="QR_STUDENT";
         String QR_TEACHER ="QR_TEACHER";
     }
}
