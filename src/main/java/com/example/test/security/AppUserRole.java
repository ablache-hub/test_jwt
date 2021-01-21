package com.example.test.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

//Roles possibles
public enum AppUserRole { //On liste les roles avec leurs permissions
    STUDENT(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(AppUserPermission.STUDENT_READ, AppUserPermission.STUDENT_WRITE)),
    ADMINTEST(Sets.newHashSet(AppUserPermission.STUDENT_READ)),
    USER(Sets.newHashSet(AppUserPermission.STUDENT_READ));

    private final Set<AppUserPermission> permissions;

    AppUserRole(Set<AppUserPermission> permissions) {
        this.permissions = permissions;
    }


    public Set<AppUserPermission> getPermissions() {
        return permissions;
    }


    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
       Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toSet());
       permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
       return permissions;
    }
}

