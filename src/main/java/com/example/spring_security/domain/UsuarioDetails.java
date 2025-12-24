package com.example.spring_security.domain;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class UsuarioDetails implements UserDetails {

    private final Usuario usuario;
    private final List<SimpleGrantedAuthority> authorities;

    public UsuarioDetails(Usuario usuario) {
        this.usuario = usuario;
        this.authorities = usuario
                .listaPermissoes()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .toList();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        for (SimpleGrantedAuthority simpleGrantedAuthority: authorities){
            System.out.println(simpleGrantedAuthority.getAuthority());
        }
        return authorities;
    }

    @Override
    public @Nullable String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
