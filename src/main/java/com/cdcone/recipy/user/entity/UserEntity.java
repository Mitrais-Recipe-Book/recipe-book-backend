package com.cdcone.recipy.user.entity;

import com.cdcone.recipy.recipe.entity.RecipeEntity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Lob
    @Column(name = "profile_photo", columnDefinition = "BYTEA")
    @Type(type = "org.hibernate.type.BinaryType")
    @JsonIgnore
    private byte[] profilePhoto;

    @Column(name = "profile_photo_type")
    private String profilePhotoType;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<RoleEntity> roles;

    @OneToMany(mappedBy = "user")
    private List<RecipeEntity> recipes;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "creator_id", referencedColumnName = "id")
    )
    private Set<UserEntity> follows;

    public UserEntity(String email, String username, String password, String fullName) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }
}
