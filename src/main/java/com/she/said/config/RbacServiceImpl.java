package com.she.said.config;

import com.she.said.entity.Permission;
import com.she.said.entity.Role;
import com.she.said.repository.RoleRepository;
import com.she.said.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author 小作坊王老板
 * @date 2021-02-08 17:14:55
 * @description to do
 */
@Component("rbacService")
@Slf4j
public class RbacServiceImpl {
    private final RoleService roleService;

    public RbacServiceImpl(RoleService roleService) {
        this.roleService = roleService;
    }

    public boolean hasPermission(HttpServletRequest request, Authentication authentication){
        Object principal=authentication.getPrincipal();
        Collection<GrantedAuthority> authorities= (Collection<GrantedAuthority>) authentication.getAuthorities();
        log.info("我要的："+principal);
        if(principal==null)
            return false;
        if(authorities==null||authorities.size()==0)
            return false;
        return hasPermission(authorities,request.getRequestURI());
    }
    private Boolean roleHasPermission(Role role,String requestUrl){
        Set<Permission> permissions=role.getPermissions();
        AntPathMatcher antPathMatcher=new AntPathMatcher();
        for (Permission permission:permissions){
            if(antPathMatcher.match(permission.getUrl(),requestUrl)){
                return true;
            }
            else {
                Deque<Permission> nodes=new ArrayDeque<>();
                Permission node=permission;
                nodes.push(node);
                while (!nodes.isEmpty()){
                    node=nodes.pop();
                    for(Permission permission1:node.getPermissions()){
                        if(antPathMatcher.match(permission1.getUrl(),requestUrl)){
                            return true;
                        }
                        else {
                            nodes.push(permission1);
                        }
                    }
                }
            }
        }
        return false;
    }
    private boolean hasPermission(Collection<GrantedAuthority> authorities, String requestUrl){
        for(GrantedAuthority authority:authorities){
            String roleName=authority.getAuthority();
            if(roleName.equals(JwtUtil.ANYONE))
                return false;
            Role role=roleService.findByRoleName(roleName);
            if(roleHasPermission(role,requestUrl)){
                return true;
            }
        }
        return false;
    }
}
