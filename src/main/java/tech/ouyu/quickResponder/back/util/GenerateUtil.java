package tech.ouyu.quickResponder.back.util;

import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.NumberUtils;
import sun.util.calendar.BaseCalendar;
import sun.util.calendar.CalendarDate;
import sun.util.calendar.Gregorian;
import tech.ouyu.quickResponder.back.constant.DescribeConstant;

import java.util.Calendar;
import java.util.Date;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-10 10:22
 * @Description: 通用生成工具类
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
@Slf4j
public class GenerateUtil {

    public static String generateStudentNum(RedisTemplate rt) {
        try {
            Integer o = (Integer) rt.opsForValue().get(DescribeConstant.RedisKeys.QR_STUDENT_NUMBER);
            Integer count = (Integer) rt.opsForValue().get(DescribeConstant.RedisKeys.QR_STUDENT_NUMBER_COUNT);
            String studentNumber = null;
            if (o == null) {
                studentNumber = generateStudentNum(1, 0);
                rt.opsForValue().set(DescribeConstant.RedisKeys.QR_STUDENT_NUMBER, 1);
                rt.opsForValue().set(DescribeConstant.RedisKeys.QR_STUDENT_NUMBER_COUNT, 1);
            }else{
                studentNumber = generateStudentNum(count, o);
                rt.opsForValue().set(DescribeConstant.RedisKeys.QR_STUDENT_NUMBER, o+1);
            }
            return studentNumber;
        } catch (Exception e) {
            Throwable cause = e.getCause();
            log.error(cause == null ? e.getMessage() : cause.getMessage(), e);
            return null;
        }
    }

    /**
     * 生成学号
     * @author ouyu
     * @date 2020-12-10
     * @param count
     * @param i
     * @return java.lang.String
     */
    private static String generateStudentNum(Integer count, int i) {
        String studentNumber;
        BaseCalendar.Date date = Gregorian.getGregorianCalendar().getCalendarDate();
        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDayOfMonth();
        StringBuilder sb = new StringBuilder(count.toString());
        studentNumber = sb.append(year).append(month).append(day).append(checkNumSize(i+1)).toString();
        log.info(year + "-" + month + "-" + day + "生成学号{}", studentNumber);
        return studentNumber;
    }

    /**
     * 检查是否满足5位不足的前面0补齐
     * @author ouyu
     */
    private static String checkNumSize(Integer o) {
        String s = o.toString();
        for (int size = s.length();size < 5;size++){
            s="0"+s;
        }
        return s;
    }

}
