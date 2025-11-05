package com.storehub.garage.admin.modules.buckets.models.general;

public record ApiBucketKeyPerm(
        boolean owner,
        boolean read,
        boolean write
) {

    public static ApiBucketKeyPerm fullAccess() {
        return new ApiBucketKeyPerm(true, true, true);
    }

    public static ApiBucketKeyPerm readOnly() {
        return new ApiBucketKeyPerm(false, true, false);
    }
}