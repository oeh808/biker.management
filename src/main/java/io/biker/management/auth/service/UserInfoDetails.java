package io.biker.management.auth.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.biker.management.auth.entity.UserRoles;

public class UserInfoDetails implements UserDetails {
    private int id;
    private String username;
    private String password;
    private List<GrantedAuthority> authorities;

    public UserInfoDetails(UserRoles userRoles) {
        id = userRoles.getUserId();
        username = userRoles.getUser().getEmail();
        password = userRoles.getUser().getPassword();
        authorities = Arrays.stream(userRoles.getRoles().toArray(new String[0]))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
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

    public int getId() {
        return id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
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
