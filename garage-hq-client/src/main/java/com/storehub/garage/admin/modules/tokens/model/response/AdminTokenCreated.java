package com.storehub.garage.admin.modules.tokens.model.response;

import com.storehub.garage.admin.modules.tokens.model.Scopes;

import java.time.Instant;
import java.util.List;

public record AdminTokenCreated(String created, Instant expiration, boolean expired, String id, String name, List<Scopes> scope, String secretToken) {
}
