package tech.ouyu.quickResponder.back.vo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import tech.ouyu.quickResponder.back.entity.User;

import java.util.Date;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-08 21:07
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
public class UserClaim extends DefaultClaims {
    private User user;

    public UserClaim() {
    }

    public UserClaim(User user) {
        this.user = user;
        setId(user.getId().toString());
//        setSubject(user.getUsername());
        setIssuer(user.getUsername());
        setIssuedAt(new Date());
    }
}
