package com.she.said.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

/**
 * @author 小作坊王老板
 * @date 2021-02-08 17:15:38
 * @description to do
 */
@Entity
@Data
public class Permission extends BaseEntity{
    private String permissionName;
    private String url;
    private String route;

    @ManyToMany
    @JoinTable(name = "she_permission_child", joinColumns = {@JoinColumn(name = "pid")},inverseJoinColumns = {@JoinColumn(name = "child_pid")})
    private Set<Permission> permissions;
}
