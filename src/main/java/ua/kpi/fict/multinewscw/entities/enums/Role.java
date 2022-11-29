package ua.kpi.fict.multinewscw.entities.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_EDITOR, ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
