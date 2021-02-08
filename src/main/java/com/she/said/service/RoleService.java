package com.she.said.service;

import com.she.said.entity.Role;
import org.springframework.stereotype.Service;

/**
 * @author 小作坊王老板
 * @date 2021-02-08 18:07:04
 * @description to do
 */
@Service
public interface RoleService {
    public Role findByRoleName(String roleName);
}
