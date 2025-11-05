package com.storehub.garage.admin.modules.buckets.models.general;

public record ApiBucketQuotas(
        int maxObjects,
        int maxSize
) {
}
