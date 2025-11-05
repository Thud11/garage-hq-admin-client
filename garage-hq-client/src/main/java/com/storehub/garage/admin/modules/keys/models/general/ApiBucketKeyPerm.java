package com.storehub.garage.admin.modules.keys.models.general;

public record ApiBucketKeyPerm(
        boolean owner,
        boolean read,
        boolean write
) {

    private static final ApiBucketKeyPerm NO_PERMISSIONS = new ApiBucketKeyPerm(false, false, false);
    private static final ApiBucketKeyPerm READ_ONLY = new ApiBucketKeyPerm(false, true, false);
    private static final ApiBucketKeyPerm WRITE_ONLY = new ApiBucketKeyPerm(false, false, true);
    private static final ApiBucketKeyPerm READ_WRITE = new ApiBucketKeyPerm(false, true, true);
    private static final ApiBucketKeyPerm FULL_OWNERSHIP = new ApiBucketKeyPerm(true, true, true);

    public static ApiBucketKeyPerm noPermissions() {
        return NO_PERMISSIONS;
    }

    public static ApiBucketKeyPerm readOnly() {
        return READ_ONLY;
    }

    public static ApiBucketKeyPerm writeOnly() {
        return WRITE_ONLY;
    }

    public static ApiBucketKeyPerm readWrite() {
        return READ_WRITE;
    }

    public static ApiBucketKeyPerm fullOwnership() {
        return FULL_OWNERSHIP;
    }
}