package com.she.said.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author 小作坊王老板
 * @date 2021-02-08 14:37:40
 * @description to do
 */
public class JwtUtil {
    public final static String TOKEN_HEADER="Authorization";
    public final static String TOKEN_PREFIX="Bearer ";
    public static final String UPDATE_TOKEN="New-Authorization";
    //token的过期时间
    private static final long EXPIRATION=36000L;
    //选择记住我之后过期时间
    private  static final  long EXPIRATION_REMEMBER=604800L;
    //秘钥
    private static final String SECRET="wangmian787";
    //签发者
    private static final String ISS="wangmian";
    //角色的key
    private static final String ROLE_CLAIMS="rol";

    private static final String REMEMBER="remember";

    //redis timeout
    private static final Integer REDIS_TIMEOUT=3000;

    private static final String REDIS_ITEM="token";

    public static final String ANYONE="anyone";

    /**
     * @description 基于用户名和多角色创建的token
     * @param username 用户名
     * @param roles 角色名list
     * @param isRemember 是否记住token
     * @return token
     */
    public static String createToken(String username, List<String> roles,boolean isRemember){
        long expiration=isRemember?EXPIRATION_REMEMBER:EXPIRATION;
        HashMap<String,Object> map=new HashMap<>();
        map.put(ROLE_CLAIMS,roles);
        map.put(REMEMBER,isRemember);
        String token= Jwts.builder()
                .signWith(SignatureAlgorithm.HS512,SECRET)
                .setClaims(map)
                .setSubject(username)
                .setIssuer(ISS)
                .setExpiration(new Date(System.currentTimeMillis()+expiration*1000))
                .compact();
        RedisUtils.put(username,REDIS_ITEM,token,0);
        return token;
    }

    @Data
    public static class loginStatus{
        private boolean hasLogin;
        private String token;

        public loginStatus(boolean hasLogin, String token) {
            this.hasLogin = hasLogin;
            this.token = token;
        }
    }
    public static loginStatus hasLogin(String token){
        String username=getUsername(token);
        boolean hasLogin= RedisUtils.hasKey(username+"_"+REDIS_ITEM);
        String tmp=(String) RedisUtils.get(username+"_"+REDIS_ITEM);
        return new loginStatus(hasLogin,tmp);
    }

    public static void logout(String username){
        RedisUtils.del(username,REDIS_ITEM);
    }

    public static boolean isRemember(String token){
        return (boolean)getTokenBody(token).get(REMEMBER);
    }

    /**
     * @description 根据token获取用户名
     * @param token
     * @return 用户名
     */
    public static String getUsername(String token){
        return getTokenBody(token).getSubject();
    }

    /**
     * @description 根据token获取角色列表
     * @param token
     * @return 角色列表
     */
    public static List<String> getRoles(String token){
        return (List<String>) getTokenBody(token).get(ROLE_CLAIMS);
    }

    public static boolean isExpiration(String token){
        Date expiration=getTokenBody(token).getExpiration();
        return expiration.after(new Date());
    }

    /**
     * @description 获取token的body
     * @param token
     * @return body of token
     */
    private static Claims getTokenBody(String token){
        token=token.replace(TOKEN_PREFIX,"");
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }
}
