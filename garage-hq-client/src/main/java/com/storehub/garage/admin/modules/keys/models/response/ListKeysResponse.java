package com.storehub.garage.admin.modules.keys.models.response;

import java.time.Instant;

public record ListKeysResponse(
        Instant created,
        Instant expiration,
        boolean expired,
        String id,
        String name
) {
}
