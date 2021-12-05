package com.krishna.jwt.jwtimplementation.entity;

import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Component
@Table(name="user_table")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "user_name")
    private String username;
    private String password;
    private boolean active = true;
    private String roles;
    @Column(name = "account_non_locked")
    private boolean accountNonLocked = true;

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setFailedAttempt(int failedAttempt) {
        this.failedAttempt = failedAttempt;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isActive() {
        return active;
    }

    public String getRoles() {
        return roles;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public int getFailedAttempt() {
        return failedAttempt;
    }

    @Column(name = "failed_attempt")
    private int failedAttempt;
}
