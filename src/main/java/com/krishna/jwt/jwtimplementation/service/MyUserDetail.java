package com.krishna.jwt.jwtimplementation.service;

import com.krishna.jwt.jwtimplementation.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MyUserDetail implements UserDetails {

    String username;
    String password;
    private boolean isActive;
    private boolean isAccountNonLocked;
    private List<GrantedAuthority> authorities;

    public MyUserDetail(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.isActive = user.isActive();
        this.isAccountNonLocked = user.isAccountNonLocked();
        this.authorities = Arrays.stream(user.getRoles().split(" "))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

    }

    public MyUserDetail() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
