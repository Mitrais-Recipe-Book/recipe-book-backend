package com.cdcone.recipy.recipe.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
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

    @Column(name = "views", nullable = false)
    private Integer views = 0;

    @JsonBackReference
    @ManyToMany(mappedBy = "tags")
    private List<RecipeEntity> recipes;

    public TagEntity(String name) {
        this.name = name;
    }
}
