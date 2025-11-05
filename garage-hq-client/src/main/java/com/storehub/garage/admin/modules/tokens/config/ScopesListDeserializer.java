package com.storehub.garage.admin.modules.tokens.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.storehub.garage.admin.modules.tokens.model.Scopes;

import java.io.IOException;
import java.util.*;

public class ScopesListDeserializer extends JsonDeserializer<List<Scopes>> {

    @Override
    public List<Scopes> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Set<Scopes> result = new HashSet<>();

        if (!p.isExpectedStartArrayToken()) {
            return Collections.emptyList();
        }

        while (p.nextToken() != JsonToken.END_ARRAY) {
            String valueFromServer = p.getText();

            if ("*".equals(valueFromServer)) {
                result.addAll(Arrays.asList(Scopes.values()));
                continue;
            }
            String normalizedFromServer = valueFromServer.replace("_", "").toLowerCase();

            boolean found = false;
            for (Scopes scopeEnum : Scopes.values()) {
                String normalizedEnumName = scopeEnum.name().replace("_", "").toLowerCase();
                if (normalizedEnumName.equals(normalizedFromServer)) {
                    result.add(scopeEnum);
                    found = true;
                    break;
                }
            }

            if (!found) {
                throw new IOException("Unknown scope value received from server: " + valueFromServer);
            }
        }

        return new ArrayList<>(result);
    }
}