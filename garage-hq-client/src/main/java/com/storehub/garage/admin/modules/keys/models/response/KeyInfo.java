package com.storehub.garage.admin.modules.keys.models.response;

import com.storehub.garage.admin.modules.keys.models.request.KeyPerm;

import java.time.Instant;
import java.util.List;

public record KeyInfo(
        String accessKeyId,
        List<KeyInfoBucketResponse> buckets,
        Instant created,
        Instant expiration,
        boolean expired,
        String name,
        KeyPerm permissions,
        String secretAccessKey

) {
}
