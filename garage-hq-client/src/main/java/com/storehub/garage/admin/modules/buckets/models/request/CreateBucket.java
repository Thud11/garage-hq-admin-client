package com.storehub.garage.admin.modules.buckets.models.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.storehub.garage.admin.modules.buckets.models.general.ApiBucketKeyPerm;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CreateBucket(String globalAlias, CreateBucketLocalAlias localAlias) {

    public CreateBucket {
        if (globalAlias == null && localAlias == null) {
            throw new IllegalArgumentException("Either globalAlias or localAlias must be provided.");
        }
    }

    public static CreateBucket withGlobalAlias(String globalAlias) {
        if (globalAlias == null || globalAlias.isBlank()) {
            throw new IllegalArgumentException("Global alias must not be null or blank.");
        }
        return new CreateBucket(globalAlias, null);
    }

    public static CreateBucket withLocalAlias(String accessKeyId, String alias, ApiBucketKeyPerm allow) {
        return new CreateBucket(null, new CreateBucketLocalAlias(accessKeyId, alias, allow));
    }

    public static CreateBucket withBoth(String globalAlias, String accessKeyId, String alias, ApiBucketKeyPerm allow) {
        if (globalAlias == null || globalAlias.isBlank()) {
            throw new IllegalArgumentException("Global alias must not be null or blank when using both aliases.");
        }
        return new CreateBucket(globalAlias, new CreateBucketLocalAlias(accessKeyId, alias, allow));
    }

    public record CreateBucketLocalAlias(String accessKeyId, String alias, ApiBucketKeyPerm allow) {
        public CreateBucketLocalAlias {
            if (alias == null || alias.isBlank()) {
                throw new IllegalArgumentException("Alias must not be null or blank.");
            }
        }
    }
}
