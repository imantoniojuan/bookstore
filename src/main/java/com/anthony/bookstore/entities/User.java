package com.anthony.bookstore.entities;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "user")
public class User extends BaseEntity implements UserDetails{

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 255, nullable = false)
    private String pHash;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getpHash() {
        return pHash;
    }

    public void setpHash(String pHash) {
        this.pHash = pHash;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return this.pHash;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}

