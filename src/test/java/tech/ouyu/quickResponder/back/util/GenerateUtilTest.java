package tech.ouyu.quickResponder.back.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-10 13:25
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
@SpringBootTest
class GenerateUtilTest {
    @Autowired
    RedisTemplate redisTemplate;

    @Test
    void generateStudentNum() {
        String studentNum = GenerateUtil.generateStudentNum(redisTemplate);
        System.out.println(studentNum);
    }
}