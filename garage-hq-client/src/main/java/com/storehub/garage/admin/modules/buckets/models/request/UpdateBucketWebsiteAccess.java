package com.storehub.garage.admin.modules.buckets.models.request;

public record UpdateBucketWebsiteAccess(
        boolean enabled,
        String errorDocument,
        String indexDocument
) {
}
