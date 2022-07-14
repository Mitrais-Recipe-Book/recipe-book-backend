package com.cdcone.recipy.recipe.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity()
@Table(name = "tags", uniqueConstraints = {
        @UniqueConstraint(name = "tag_unique", columnNames = "name")
})
public class TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @JsonIgnore
    @Column(name = "views", nullable = false)
    private Integer views = 0;

    public TagEntity(String name) {
        this.name = name;
    }
}
