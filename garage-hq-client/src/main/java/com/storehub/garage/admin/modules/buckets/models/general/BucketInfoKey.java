package com.storehub.garage.admin.modules.buckets.models.general;

public record BucketInfoKey(
        String accessKeyId,
        String bucketLocalAlias,
        String name,
        ApiBucketKeyPerm permissions

) {
}
