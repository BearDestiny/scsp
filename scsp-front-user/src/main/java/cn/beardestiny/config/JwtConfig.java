package cn.beardestiny.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.Date;

/**
 * JWT的token，区分大小写
 */
@Component
@ConfigurationProperties(prefix = "config.jwt")
public class JwtConfig {

    private String secret;
    private long expire;
    private String header;

    /**
     * 生成token
     * @param role 用户角色
     * @param user_id 用户id
     * @return String
     */
    public String createToken (String role, String user_id){
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + expire * 1000 * 24);    //过期时间24h

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .claim("role",role)
                .setSubject(user_id)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    /**
     * 获取token中注册信息
     * @param token
     * @return
     */
    public Claims getTokenClaim (String token) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
        } catch (ExpiredJwtException e){
                    return e.getClaims(); //防止jwt过期解析报错
            }
        }

    /**
     * 验证token是否过期失效
     * @param expirationTime
     * @return
     */
    public boolean isTokenExpired (Date expirationTime) {
        return expirationTime.before(new Date());
    }

    /**
     * 获取token失效时间
     * @param token
     * @return
     */
    public Date getExpirationDateFromToken(String token) {
        return getTokenClaim(token).getExpiration();
    }
    /**
     * 获取用户名从token中
     */
    public String getUser_id(String token) {
        return getTokenClaim(token).getSubject();
    }

    /**
     * 获取jwt发布时间
     */
    public Date getIssuedAtDateFromToken(String token) {
        return getTokenClaim(token).getIssuedAt();
    }

    // --------------------- getter & setter ---------------------

    public String getSecret() {
        return secret;
    }
    public void setSecret(String secret) {
        this.secret = secret;
    }
    public long getExpire() {
        return expire;
    }
    public void setExpire(long expire) {
        this.expire = expire;
    }
    public String getHeader() {
        return header;
    }
    public void setHeader(String header) {
        this.header = header;
    }
}