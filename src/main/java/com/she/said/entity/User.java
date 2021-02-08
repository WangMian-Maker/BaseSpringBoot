package com.she.said.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

/**
 * @author 小作坊王老板
 * @date 2021-02-08 17:15:24
 * @description to do
 */
@Entity
@Data
@Table(name = "she_user")
public class User extends BaseEntity{
    private String username;
    private String password;

    @ManyToMany
    @JoinTable(name = "she_user_roles", joinColumns = {@JoinColumn(name = "uid")},inverseJoinColumns = {@JoinColumn(name = "rid")})
    private Set<Role> roles;
}
