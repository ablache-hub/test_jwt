package com.example.test.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

//Roles possibles
public enum ApplicationUserRole { //On liste les roles avec leurs permissions
    STUDENT(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(ApplicationUserPermission.STUDENT_READ, ApplicationUserPermission.STUDENT_WRITE)),
    ADMINTEST(Sets.newHashSet(ApplicationUserPermission.STUDENT_READ));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
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

