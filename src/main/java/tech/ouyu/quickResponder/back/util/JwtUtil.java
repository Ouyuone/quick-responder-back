package tech.ouyu.quickResponder.back.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * <pre>
 * @Auther: ousakai
 * @Date: 2020-12-08 14:22
 * @Description:
 * 修改版本: 1.0
 * 修改日期:
 * 修改人 :
 * 修改说明: 初步完成
 * 复审人 :
 * </pre>
 */
@Component
@Slf4j
public class JwtUtil {
    @Value("${jwt.secret:aaabbbcccdddeeefffggghhhiiijjjkkklllmmmnnnooopppqqqrrrsssttt}")
    private String secret;
    @Value("${jwt.expire-time-in-second:1800}")
    private Long expirationTimeInSecond;

    /**
     * 生成token
     *
     * @param claims
     * @return java.lang.String
     * @author ouyu
     * @date 2020-12-08
     */
    public String generateToken(Claims claims) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.builder().signWith(key, SignatureAlgorithm.HS256)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(getExpirationDate())
                .compact();
    }


    public String generateToken(Long userId) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.builder().signWith(key, SignatureAlgorithm.HS256)
                .setId(userId.toString())
//                .setSubject(userId.toString())
                .setIssuer(userId.toString())
                .setIssuedAt(new Date())
                .setExpiration(getExpirationDate())
                .compact();
    }

    /**
     * 过期时间
     * @author ouyu
     */
    private Date getExpirationDate() {
        return new Date(System.currentTimeMillis() + expirationTimeInSecond * 1000);
    }

    public Claims getClaims(String token){
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
//        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        Jws<Claims> claimsJws = null;
        try {
            claimsJws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
        } catch (ExpiredJwtException | IllegalArgumentException | SignatureException | MalformedJwtException | UnsupportedJwtException e) {
            log.error("token valid error",e);
            throw new IllegalArgumentException("token valid error");
        }
        return claimsJws.getBody();
    }

    /**
     * 验证token是否有效
     * @author ouyu
     */
    public Boolean validExpiration(String token){
        Claims claims = getClaims(token);
        Date expiration = null;
        expiration = claims.getExpiration();
        return expiration.after(new Date());
    }

    public static void main(String[] args) {
        JwtUtil jwtUtil = new JwtUtil();
        String token = jwtUtil.generateToken(1L);

        Claims claims = jwtUtil.getClaims(token);
        System.out.println(claims);
        Boolean expiration = jwtUtil.validExpiration(token);
        System.out.println(expiration);
    }

}
