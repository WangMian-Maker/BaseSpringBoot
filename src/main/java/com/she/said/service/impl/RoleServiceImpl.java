package com.she.said.service.impl;

import com.she.said.entity.Role;
import com.she.said.repository.RoleRepository;
import com.she.said.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 小作坊王老板
 * @date 2021-02-08 18:37:00
 * @description to do
 */

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}
