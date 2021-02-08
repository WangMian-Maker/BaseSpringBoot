package com.she.said.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

/**
 * @author 小作坊王老板
 * @date 2021-02-08 17:15:32
 * @description to do
 */
@Entity
@Data
public class Role extends BaseEntity{
    private String roleName;

    @ManyToMany
    @JoinTable(name = "she_user_roles", joinColumns = {@JoinColumn(name = "rid")},inverseJoinColumns = {@JoinColumn(name = "uid")})
    private Set<User> users;

    @ManyToMany
    @JoinTable(name = "she_role_permissions", joinColumns = {@JoinColumn(name = "rid")},inverseJoinColumns = {@JoinColumn(name = "pid")})
    private Set<Permission> permissions;
}
