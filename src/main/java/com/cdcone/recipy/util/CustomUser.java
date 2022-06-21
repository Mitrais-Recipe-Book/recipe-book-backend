package com.cdcone.recipy.util;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class CustomUser extends User {
    private static final long serialVersionUID = 4945469645845065625L;
    private final long id;
    private final String email;
    private final String fullName;

    public CustomUser( long id, String username, String password, String email,
                       String fullName, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
        this.email = email;
        this.fullName = fullName;
    }
}
