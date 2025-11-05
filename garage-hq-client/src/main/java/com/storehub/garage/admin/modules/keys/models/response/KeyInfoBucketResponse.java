package com.storehub.garage.admin.modules.keys.models.response;

import java.util.List;

public record KeyInfoBucketResponse(
        List<String> globalAliases,
        String id,
        List<String> localAliases,
        List<KeyInfoBucketResponse> permissions
) {
}
