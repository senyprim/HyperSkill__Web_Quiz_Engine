package engine.model;

import org.springframework.security.core.GrantedAuthority;

public enum  Role implements GrantedAuthority {
    USER("ROLE_USER");
    private String role;
    Role(String str){
        this.role=str;
    }
    @Override
    public String getAuthority() {
        return role;
    }
}
