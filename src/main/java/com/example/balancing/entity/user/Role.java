package com.example.balancing.entity.user;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {

    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_MODERATOR("ROLE_MODERATOR"),
    ROLE_USER("ROLE_USER"),
    DELETED_ACCOUNT("DELETED_ACCOUNT");

    private final String authority;

    public String getAuthority() {
        return authority;
    }
}
