package com.storehub.garage.admin.modules.tokens.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.storehub.garage.admin.modules.tokens.model.Scopes;

import java.time.Instant;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CreateOrUpdateAdminToken(Instant expiration, String name, boolean neverExpires, List<Scopes> scope) {

    public CreateOrUpdateAdminToken {
        if (neverExpires && expiration != null) {
            throw new IllegalArgumentException("Cannot set an expiration date for a token that never expires.");
        }
        if (!neverExpires && expiration == null) {
            throw new IllegalArgumentException("Expiration date must be set for a token that expires.");
        }
    }

    public static CreateOrUpdateAdminToken thatExpiresAt(String name, Instant expiration, List<Scopes> scope) {
        return new CreateOrUpdateAdminToken(expiration, name, false, scope);
    }

    public static CreateOrUpdateAdminToken thatNeverExpires(String name, List<Scopes> scope) {
        return new CreateOrUpdateAdminToken(null, name, true, scope);
    }
}