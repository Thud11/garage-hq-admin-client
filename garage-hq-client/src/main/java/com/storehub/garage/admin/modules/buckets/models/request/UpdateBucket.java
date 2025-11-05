package com.storehub.garage.admin.modules.buckets.models.request;

import com.storehub.garage.admin.modules.buckets.models.general.ApiBucketQuotas;

public record UpdateBucket(
        String id,
        ApiBucketQuotas quotas,
        UpdateBucketWebsiteAccess websiteAccess
) {
}
