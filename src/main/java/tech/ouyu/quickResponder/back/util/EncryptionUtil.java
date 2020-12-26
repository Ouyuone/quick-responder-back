package tech.ouyu.quickResponder.back.util;

import org.springframework.util.DigestUtils;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-08 13:38
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
public class EncryptionUtil {


    /**
     * md5加密
     * @author ouyu
     * @date 2020-12-08
     * @param chars
     * @param salt
     * @return java.lang.String
     */
    public static String encryptionMd5(String chars, String salt){
        return encryption(chars,salt,"md5");
    }

    /**
     * 加密
     * @author ouyu
     * @date 2020-12-08
     * @param chars
     * @param salt 盐
     * @param type  加密类型
     * @return java.lang.String
     */
    public static String encryption(String chars, String salt,String type){
        return DigestUtils.md5DigestAsHex((chars + salt + type).getBytes());
    }

}
