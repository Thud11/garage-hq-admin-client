package com.storehub.garage.admin.modules.buckets.models.response;

import java.time.Instant;
import java.util.List;

public record BucketForListResponse(
        Instant created,
        List<String> globalAliases,
        String id,
        List<BucketLocalAlias> localAliases
) {
}
