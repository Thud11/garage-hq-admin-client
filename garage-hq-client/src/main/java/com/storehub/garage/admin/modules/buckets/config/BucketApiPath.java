package com.storehub.garage.admin.modules.buckets.config;


import lombok.experimental.UtilityClass;

@UtilityClass
public class BucketApiPath {

    public static final String CLEANUP_INCOMPLETE_UPLOADS = "/v2/CleanupIncompleteUploads";
    public static final String CREATE_BUCKET = "/v2/CreateBucket";
    public static final String DELETE_BUCKET = "/v2/DeleteBucket";
    public static final String GET_BUCKET_INFO = "/v2/GetBucketInfo";
    public static final String INSPECT_OBJECT_IN_BUCKET = "/v2/InspectObject";
    public static final String LIST_BUCKETS = "/v2/ListBuckets";
    public static final String UPDATE_BUCKET = "/v2/UpdateBucket";
}