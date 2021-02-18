package com.she.said;

import com.she.said.entity.Permission;
import com.she.said.entity.Role;
import com.she.said.entity.User;
import com.she.said.service.PermissionService;
import com.she.said.service.RoleService;
import com.she.said.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 小作坊王老板
 * @date 2021-02-09 17:31:14
 * @description to do
 */
@Component
public class Init {
    private final UserService userService;
    private final RoleService roleService;
    private final PermissionService permissionService;
    private final PasswordEncoder passwordEncoder;

    public Init(UserService userService, RoleService roleService, PermissionService permissionService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.permissionService = permissionService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    private void userInit(){
        Permission permissionAdmin=permissionService.findByUrl("/");
        Role roleAdmin=roleService.findByRoleName("role_admin");
        User userAdmin=userService.findByUserName("user_admin");
        if(permissionAdmin==null){
            permissionAdmin=new Permission();
            permissionAdmin.setPermissionName("permission_admin");
            permissionAdmin.setUrl("/**");
            permissionAdmin.setRoute("/");
            permissionAdmin.setId(1l);
            permissionService.save(permissionAdmin);
        }
        if(roleAdmin==null){
            roleAdmin=new Role();
            roleAdmin.setRoleName("role_admin");
            Set<Permission> permissions=new HashSet<>();
            permissions.add(permissionAdmin);
            roleAdmin.setPermissions(permissions);
            roleAdmin.setId(1l);
            roleService.save(roleAdmin);
        }
        if(userAdmin==null){
            userAdmin=new User();
            userAdmin.setUsername("user_admin");
            userAdmin.setPassword("wangmian787");
            Set<Role> roles=new HashSet<>();
            roles.add(roleAdmin);
            userAdmin.setId(1l);
            userAdmin.setRoles(roles);
            userService.save(userAdmin);
        }
    }
}
