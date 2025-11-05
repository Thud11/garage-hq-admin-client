package com.storehub.garage.admin.modules.buckets.internal;

import com.storehub.garage.admin.modules.buckets.config.BucketApiPath;
import com.storehub.garage.admin.modules.buckets.models.request.CleanupIncompleteUploads;
import com.storehub.garage.admin.modules.buckets.models.request.CreateBucket;
import com.storehub.garage.admin.modules.buckets.models.request.UpdateBucket;
import com.storehub.garage.admin.modules.buckets.models.response.BucketForListResponse;
import com.storehub.garage.admin.modules.buckets.models.response.BucketInfoResponse;
import com.storehub.garage.admin.modules.buckets.models.response.InspectObject;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

public interface BucketApi {

    @POST(BucketApiPath.CLEANUP_INCOMPLETE_UPLOADS)
    Call<Void> cleanupIncompleteUploads(@Body CleanupIncompleteUploads cleanupIncompleteUploads);

    @POST(BucketApiPath.CREATE_BUCKET)
    Call<BucketInfoResponse> createBucket(@Body CreateBucket createBucket);

    @POST(BucketApiPath.DELETE_BUCKET)
    Call<Void> deleteBucket(@Query("id") String id);

    @GET(BucketApiPath.GET_BUCKET_INFO)
    Call<BucketInfoResponse> getBucketInfo(@Query("id") String id);

    @GET(BucketApiPath.INSPECT_OBJECT_IN_BUCKET)
    Call<InspectObject> inspectObjectInBucket(@Query("bucketId") String id, @Query("key") String objectKey);

    @GET(BucketApiPath.LIST_BUCKETS)
    Call<List<BucketForListResponse>> getAllBuckets();

    @POST(BucketApiPath.UPDATE_BUCKET)
    Call<BucketInfoResponse> updateBucket(@Query("id") String id, @Body UpdateBucket request);
}
