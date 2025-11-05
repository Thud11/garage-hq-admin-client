package com.storehub.garage.admin.modules.buckets;

import com.storehub.garage.admin.executor.GarageApiCallExecutor;
import com.storehub.garage.admin.modules.buckets.internal.BucketApi;
import com.storehub.garage.admin.modules.buckets.models.request.CleanupIncompleteUploads;
import com.storehub.garage.admin.modules.buckets.models.request.CreateBucket;
import com.storehub.garage.admin.modules.buckets.models.request.UpdateBucket;
import com.storehub.garage.admin.modules.buckets.models.response.BucketForListResponse;
import com.storehub.garage.admin.modules.buckets.models.response.BucketInfoResponse;

import java.util.List;

@SuppressWarnings("ClassCanBeRecord")
public class GarageBucketClient {


    private final BucketApi api;

    public GarageBucketClient(BucketApi api) {
        this.api = api;
    }


    public BucketInfoResponse createBucket(CreateBucket request) {
        return GarageApiCallExecutor.execute(() -> api.createBucket(request).execute()).body();
    }

    public boolean cleanupIncompleteUploads(CleanupIncompleteUploads request) {
        return GarageApiCallExecutor.execute(() -> api.cleanupIncompleteUploads(request).execute()).isSuccessful();
    }

    public boolean deleteBucket(String id) {
        return GarageApiCallExecutor.execute(() -> api.deleteBucket(id).execute()).isSuccessful();
    }

    public BucketInfoResponse getBucketInfo(String id) {
        return GarageApiCallExecutor.execute(() -> api.getBucketInfo(id).execute()).body();
    }

    public boolean inspectObjectInBucket(String id, String objectKey) {
        return GarageApiCallExecutor.execute(() -> api.inspectObjectInBucket(id, objectKey).execute()).isSuccessful();
    }

    public List<BucketForListResponse> getAllBuckets() {
        return GarageApiCallExecutor.execute(() -> api.getAllBuckets().execute()).body();
    }

    public BucketInfoResponse updateBucket(String id, UpdateBucket request) {
        return GarageApiCallExecutor.execute(()-> api.updateBucket(id, request).execute()).body();
    }
}
