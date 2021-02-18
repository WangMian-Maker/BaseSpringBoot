package com.she.said.repository;

import com.she.said.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author 小作坊王老板
 * @date 2021-02-08 17:57:22
 * @description to do
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission,Long> {

    @Query(value = "select p from Permission p where p.url=?1")
    public Permission findByUrl(String url);
}
