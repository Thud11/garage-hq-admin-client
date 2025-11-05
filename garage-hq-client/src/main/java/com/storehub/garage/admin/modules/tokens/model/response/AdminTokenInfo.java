package com.storehub.garage.admin.modules.tokens.model.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.storehub.garage.admin.modules.tokens.config.ScopesListDeserializer;
import com.storehub.garage.admin.modules.tokens.model.Scopes;

import java.time.Instant;
import java.util.List;

public record AdminTokenInfo(String created, Instant expiration, boolean expired, String id, String name, @JsonDeserialize(using = ScopesListDeserializer.class) List<Scopes> scope) {
}
