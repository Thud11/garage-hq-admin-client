package com.storehub.garage.admin.models;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;

public record AdminApiContext(
        URI adminApiUri,
        String masterToken,
        ObjectMapper objectMapper
) {}
