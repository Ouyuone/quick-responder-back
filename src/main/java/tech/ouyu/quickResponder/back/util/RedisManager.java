package tech.ouyu.quickResponder.back.util;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-13 10:32
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
@AllArgsConstructor
@Slf4j
public class RedisManager {
    final RedisTemplate redisTemplate;

    public  <T> T getKey(String key) {
        try {
            Object o = getopv().get(key);
            return o == null ?null:(T)o;
        } catch (Exception e) {
            Throwable cause = e.getCause();
            log.error(cause == null ? e.getMessage() : cause.getMessage(), e);
        }
        return null;
    }

    public void setKey(String key, String code) {
        try {
            getopv().set(key,code,60*5, TimeUnit.SECONDS);
        } catch (Exception e) {
            Throwable cause = e.getCause();
            log.error(cause == null ? e.getMessage() : cause.getMessage(), e);
        }
    }
    private ValueOperations getopv(){
        return redisTemplate.opsForValue();
    }

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }
}
