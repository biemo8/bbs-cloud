package com.biemo.cloud.auth.modular.sso.util;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.Map;

/**
 * <p>jwt token工具类</p>
 * <pre>
 *     jwt的claim里一般包含以下几种数据:
 *         1. iss -- token的发行者
 *         2. sub -- 该JWT所面向的用户
 *         3. aud -- 接收该JWT的一方
 *         4. exp -- token的失效时间
 *         5. nbf -- 在此时间段之前,不会被处理
 *         6. iat -- jwt发布时间
 *         7. jti -- jwt唯一标识,防止重复使用
 * </pre>
 *
 *
 * @Date 2017/8/25 10:59
 */
public class JwtTokenUtil {

    /**
     * 获取用户名从token中
     */
    public static String getSubjectFromToken(String token, String jwtSecret) {
        return getClaimFromToken(token, jwtSecret).getSubject();
    }

    /**
     * 获取jwt发布时间
     */
    public Date getIssuedAtDateFromToken(String token, String jwtSecret) {
        return getClaimFromToken(token, jwtSecret).getIssuedAt();
    }

    /**
     * 获取jwt失效时间
     */
    public static Date getExpirationDateFromToken(String token, String jwtSecret) {
        return getClaimFromToken(token, jwtSecret).getExpiration();
    }

    /**
     * 获取jwt接收者
     */
    public String getAudienceFromToken(String token, String jwtSecret) {
        return getClaimFromToken(token, jwtSecret).getAudience();
    }

    /**
     * 获取私有的jwt claim
     */
    public String getPrivateClaimFromToken(String token, String key, String jwtSecret) {
        return getClaimFromToken(token, jwtSecret).get(key).toString();
    }

    /**
     * 获取jwt的payload部分
     */
    public static Claims getClaimFromToken(String token, String jwtSecret) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 解析token是否正确(true-正确, false-错误)<br>
     */
    public static Boolean checkToken(String token, String jwtSecret) throws JwtException {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    /**
     * <pre>
     *  验证token是否失效
     *  true:过期   false:没过期
     * </pre>
     */
    public static Boolean isTokenExpired(String token, String jwtSecret) {
        try {
            final Date expiration = getExpirationDateFromToken(token, jwtSecret);
            return expiration.before(new Date());
        } catch (ExpiredJwtException expiredJwtException) {
            return true;
        }
    }

    /**
     * 生成token,根据userId和默认过期时间
     */
    public static String generateToken(String userId, Map<String, Object> claims, String jwtSecret, Integer expiredDate) {
        final Date expirationDate = new Date(System.currentTimeMillis() + expiredDate.longValue() * 1000);
        return generateToken(userId, expirationDate, claims, jwtSecret);
    }

    /**
     * 生成token,根据userId和过期时间
     */
    public static String generateToken(String userId, Date expiredDate, Map<String, Object> claims, String jwtSecret) {
        final Date createdDate = new Date();
        if (claims == null) {
            return Jwts.builder()
                    .setSubject(userId)
                    .setIssuedAt(createdDate)
                    .setExpiration(expiredDate)
                    .signWith(SignatureAlgorithm.HS512, jwtSecret)
                    .compact();
        } else {
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(userId)
                    .setIssuedAt(createdDate)
                    .setExpiration(expiredDate)
                    .signWith(SignatureAlgorithm.HS512, jwtSecret)
                    .compact();
        }
    }
}
