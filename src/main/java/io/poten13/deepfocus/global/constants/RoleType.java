package io.poten13.deepfocus.global.constants;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

@Getter
public enum RoleType {
    ROLE_USER(Set.of(new SimpleGrantedAuthority("ROLE_USER")));
    private final Set<GrantedAuthority> authorities;

    RoleType(Set<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
