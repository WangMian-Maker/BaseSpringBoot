package com.she.said.repository;

import com.she.said.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author 小作坊王老板
 * @date 2021-02-08 17:55:30
 * @description to do
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query(value = "select u from User u where u.username=?1")
    public User findByUsername(String username);
}
