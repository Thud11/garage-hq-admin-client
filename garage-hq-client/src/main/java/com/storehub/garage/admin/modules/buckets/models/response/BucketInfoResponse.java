package com.storehub.garage.admin.modules.buckets.models.response;

import com.storehub.garage.admin.modules.buckets.models.general.ApiBucketQuotas;
import com.storehub.garage.admin.modules.buckets.models.general.BucketInfoKey;
import com.storehub.garage.admin.modules.buckets.models.general.BucketInfoWebsiteResponse;

import java.time.Instant;
import java.util.List;

public record BucketInfoResponse(
        int bytes,
        Instant created,
        List<String> globalAliases,
        String id,
        List<BucketInfoKey> keys,
        int objects,
        ApiBucketQuotas quotas,
        int unfinishedMultipartUploadBytes,
        int unfinishedMultipartUploadParts,
        int unfinishedMultipartUploads,
        int unfinishedUploads,
        boolean websiteAccess,
        BucketInfoWebsiteResponse websiteConfig

) {
}
