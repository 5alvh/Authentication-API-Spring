package com.renthub.original.dto;

import com.renthub.original.enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponse {
    private String jwt;

    private UserRole userRole;

    private Long userId;

    public AuthenticationResponse(String jwt, UserRole userRole, Long userId) {
        this.jwt = jwt;
        this.userRole = userRole;
        this.userId = userId;
    }

    public AuthenticationResponse() {
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
