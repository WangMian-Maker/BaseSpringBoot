package com.she.said.repository;

import com.she.said.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author 小作坊王老板
 * @date 2021-02-08 17:56:49
 * @description to do
 */
@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    @Query(value = "select r from Role r where r.roleName=?1")
    public Role findByRoleName(String roleName);
}
