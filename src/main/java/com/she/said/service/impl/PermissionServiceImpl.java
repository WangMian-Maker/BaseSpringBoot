package com.she.said.service.impl;

import com.she.said.entity.Permission;
import com.she.said.repository.PermissionRepository;
import com.she.said.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 小作坊王老板
 * @date 2021-02-09 17:33:48
 * @description to do
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;

    public PermissionServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public Permission findByUrl(String url) {
        return permissionRepository.findByUrl(url);
    }

    @Override
    public void save(Permission permission) {
        permissionRepository.save(permission);
    }
}
