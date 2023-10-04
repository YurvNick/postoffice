package com.example.postoffice.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "department")
public class Department extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "department_address", nullable = false)
    private String departmentAddress;

    @Column(name = "index", nullable = false, unique = true, length = 6)
    private Integer index;
}