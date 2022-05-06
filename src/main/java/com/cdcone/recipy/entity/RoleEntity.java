package com.cdcone.recipy.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "roles")
@Table()
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
}
