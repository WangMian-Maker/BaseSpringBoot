package com.she.said.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * @author 小作坊王老板
 * @date 2021-02-08 14:41:09
 * @description to do
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token=request.getHeader(JwtUtil.TOKEN_HEADER);
        //如果请求头中不存在token直接放行
        if(token==null||!token.startsWith(JwtUtil.TOKEN_PREFIX)){
            SecurityContextHolder.getContext().setAuthentication(getAnyoneAuthentication());
            chain.doFilter(request,response);
            return;
        }
        JwtUtil.loginStatus loginStatus=JwtUtil.hasLogin(token);
        //如果此token的持有者在登录状态
        if(loginStatus.isHasLogin()){

            //对比此token与服务器缓存内的token是否一致
            String serverToken=loginStatus.getToken().replace(JwtUtil.TOKEN_PREFIX,"");
            String clientToken=token.replace(JwtUtil.TOKEN_PREFIX,"");

            //不一致放行
            if(!serverToken.equals(clientToken)){
                SecurityContextHolder.getContext().setAuthentication(getAnyoneAuthentication());
                chain.doFilter(request,response);
                return;
            }

            //查看token是否过期
            if(JwtUtil.isExpiration(token)){

                //是否选择了记住密码
                if(JwtUtil.isRemember(serverToken)){
                    //记住密码则重新创建一个token
                    String username=JwtUtil.getUsername(token);
                    List<String> roles=(List<String>) JwtUtil.getRoles(token);
                    token=JwtUtil.createToken(username,roles,true);
                }
                //没记住密码则放行
                else {
                    SecurityContextHolder.getContext().setAuthentication(getAnyoneAuthentication());
                    chain.doFilter(request,response);
                    return;
                }
            }
        }
        else {
            SecurityContextHolder.getContext().setAuthentication(getAnyoneAuthentication());
            chain.doFilter(request,response);
            return;
        }
        //返回
        response.addHeader(JwtUtil.UPDATE_TOKEN,JwtUtil.TOKEN_PREFIX+token);
        //解析token
        SecurityContextHolder.getContext().setAuthentication(getAuthentication(token));
        super.doFilterInternal(request, response, chain);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token){
        String username=JwtUtil.getUsername(token);
        List<String> roles=JwtUtil.getRoles(token);
        Collection<GrantedAuthority> authorities=new HashSet<>();
        for(String role:roles){
            authorities.add(new SimpleGrantedAuthority(role));
        }
        if(username!=null){
            return new UsernamePasswordAuthenticationToken(username,null,authorities);
        }
        return null;
    }

    private UsernamePasswordAuthenticationToken getAnyoneAuthentication(){
        Collection<GrantedAuthority> authorities=new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(JwtUtil.ANYONE));
        return new UsernamePasswordAuthenticationToken(JwtUtil.ANYONE,null,authorities);
    }
}
