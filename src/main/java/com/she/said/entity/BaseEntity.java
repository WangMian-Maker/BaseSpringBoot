package com.she.said.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author 小作坊王老板
 * @date 2021-02-08 17:25:37
 * @description to do
 */

@MappedSuperclass
@Data
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
