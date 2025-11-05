package com.storehub.garage.admin.modules.buckets;

import com.storehub.garage.admin.helpers.GarageTestClientFactory;
import com.storehub.garage.admin.modules.buckets.models.general.ApiBucketQuotas;
import com.storehub.garage.admin.modules.buckets.models.request.CleanupIncompleteUploads;
import com.storehub.garage.admin.modules.buckets.models.request.CreateBucket;
import com.storehub.garage.admin.modules.buckets.models.request.UpdateBucket;
import com.storehub.garage.admin.modules.buckets.models.response.BucketForListResponse;
import com.storehub.garage.admin.modules.buckets.models.response.BucketInfoResponse;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Locale;
import java.util.Random;

@Slf4j
@DisplayName("Bucket API Integration Tests")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GarageBucketClientE2EIT {

    private static final String GLOBAL_ALIAS_PREFIX = generateUniquePrefix("test-global-alias-");
    private static final String LOCAL_ALIAS_PREFIX = generateUniquePrefix("test-local-alias-");

    private static GarageBucketClient client;

    @BeforeAll
    static void setupClient() {
        client = GarageTestClientFactory.createClient(GarageBucketClient.class);
    }

    @AfterAll
    void cleanupBuckets() {
        List<BucketForListResponse> buckets = client.getAllBuckets();
        buckets.forEach(bucket -> {
            if (bucket.globalAliases().stream().anyMatch(alias -> alias.startsWith(GLOBAL_ALIAS_PREFIX))) {
                client.deleteBucket(bucket.id());
            }
        });
    }

    @Test
    @DisplayName("Should create bucket")
    void  createBucket() {
        // Arrange
        String bucketName = generateBucketName();
        CreateBucket request = CreateBucket.withGlobalAlias(bucketName);

        //Act
        BucketInfoResponse response =  client.createBucket(request);

        //Assert
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.globalAliases().getFirst()).isEqualTo(bucketName);
        Assertions.assertThat(response.id()).isNotEmpty();

    }

    @Test
    @DisplayName("Should delete bucket")
    void  shouldDeleteBucket() {
        // Arrange
        String bucketName = generateBucketName();
        CreateBucket  bucketRequest = CreateBucket.withGlobalAlias(bucketName);
        BucketInfoResponse bucketResponse = client.createBucket(bucketRequest);

        //Act
        boolean response = client.deleteBucket(bucketResponse.id());

        //Assert
        Assertions.assertThat(response).isTrue();
    }

    @Test
    @DisplayName("Should get bucket info by id")
    void   shouldGetBucketInfo() {
        // Arrange
        String bucketName = generateBucketName();
        CreateBucket  bucketRequest = CreateBucket.withGlobalAlias(bucketName);
        BucketInfoResponse bucketResponse = client.createBucket(bucketRequest);

        //Act
        BucketInfoResponse response = client.getBucketInfo(bucketResponse.id());

        //Assert
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.id()).isEqualTo(bucketResponse.id());
    }

    @Disabled("Currently cannot be tested because Admin API doesn't support object creation in buckets.")
    @Test
    @DisplayName("Should inspect object in bucket")
    void   shouldInspectObjectInBucket() {
        // Arrange
        String bucketName = generateBucketName();
        CreateBucket  bucketRequest = CreateBucket.withGlobalAlias(bucketName);
        BucketInfoResponse bucketResponse = client.createBucket(bucketRequest);

        //Act
        boolean response = client.inspectObjectInBucket(bucketResponse.id(), "object-id");

        //Assert
        Assertions.assertThat(response).isFalse();
    }

    @Test
    @DisplayName("Get all buckets")
    void shouldGetAllBuckets() {
        // Arrange
        String bucketName = generateBucketName();
        CreateBucket bucketRequest = CreateBucket.withGlobalAlias(bucketName);
        client.createBucket(bucketRequest);

        // Act
        List<BucketForListResponse> response = client.getAllBuckets();

        // Assert
        Assertions.assertThat(response).isNotEmpty();

    }

    @Test
    @DisplayName("Should update bucket")
    void  shouldUpdateBucket() {
        // Arrange
        String bucketName = generateBucketName();
        CreateBucket  bucketRequest = CreateBucket.withGlobalAlias(bucketName);
        ApiBucketQuotas quotas = new ApiBucketQuotas(10, 10);
        BucketInfoResponse bucketResponse = client.createBucket(bucketRequest);

        //Act

        UpdateBucket request = new UpdateBucket(bucketResponse.id(), quotas, null);
        BucketInfoResponse response = client.updateBucket(bucketResponse.id(), request);

        //Assert
        BucketInfoResponse actual = client.getBucketInfo(bucketResponse.id());
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(request.quotas().maxObjects()).isEqualTo(actual.quotas().maxObjects());

    }


    @Test
    @DisplayName("Should cleanup incomplete uploads")
    void  shouldCleanupIncompleteUploads() {
        // Arrange
        String bucketName = generateBucketName();
        CreateBucket  bucketRequest = CreateBucket.withGlobalAlias(bucketName);
        BucketInfoResponse bucketResponse = client.createBucket(bucketRequest);

        //Act
        CleanupIncompleteUploads request = new CleanupIncompleteUploads(bucketResponse.id(), 30);
        boolean response = client.cleanupIncompleteUploads(request);

        //Assert
        Assertions.assertThat(response).isTrue();
    }

    private String generateBucketName() {
        return GLOBAL_ALIAS_PREFIX + randomPart();
    }

    private static String generateUniquePrefix(String base) {
        return (base + randomPart() + "-").toLowerCase(Locale.ROOT);
    }

    private static String randomPart() {
        long nanos = System.nanoTime();
        int rand = new Random().nextInt(0xFFFF);
        return Long.toHexString(nanos) + Integer.toHexString(rand);
    }
}
