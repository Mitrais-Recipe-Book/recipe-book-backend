package com.cdcone.recipy.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.Type;

import java.util.Set;

@Data
@Entity()
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
    @Column(name = "profile_photo")
    @Type(type = "org.hibernate.type.BinaryType")
    @JsonIgnore
    private byte[] profilePhoto;

    @Column(name = "profile_photo_type")
    private String profilePhotoType;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<RoleEntity> roles;

    @OneToMany(mappedBy = "user")
    private Set<RecipeEntity> recipes;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "creator_id", referencedColumnName = "id")
    )
    private Set<UserEntity> follows;

    public void addFollow(UserEntity creator){
        follows.add(creator);
    }
}
