package com.she.said.service;

import com.she.said.entity.Permission;
import org.springframework.stereotype.Service;

/**
 * @author 小作坊王老板
 * @date 2021-02-09 17:33:29
 * @description to do
 */
@Service
public interface PermissionService {
    public Permission findByUrl(String url);
    public void save(Permission permission);
}
