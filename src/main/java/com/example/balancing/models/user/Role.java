package com.example.balancing.models.user;

import lombok.RequiredArgsConstructor;

/**
 * Перечисление, представляющее возможные роли.
 */
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
