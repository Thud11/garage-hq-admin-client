package com.storehub.garage.admin.modules.keys;

import com.storehub.garage.admin.helpers.GarageTestClientFactory;
import com.storehub.garage.admin.modules.keys.models.request.CreateOrUpdateKey;
import com.storehub.garage.admin.modules.keys.models.request.ImportKey;
import com.storehub.garage.admin.modules.keys.models.response.KeyInfo;
import com.storehub.garage.admin.modules.keys.models.response.ListKeysResponse;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Slf4j
@DisplayName("Keys API Integration Tests")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GarageAccessKeyClientE2EIT {


    private static GarageAccessKeyClient client;

    @BeforeAll
    static void setupClient() {
        client = GarageTestClientFactory.createClient(GarageAccessKeyClient.class);
    }

    @AfterAll
    static void cleanTestKeys(){
        List<ListKeysResponse> keys = client.getAllKeys();
        keys.forEach(key -> {
            if(key.name().startsWith("test")){
                client.deleteKey(key.id());
            }
        });
    }



    @Test
    @DisplayName("Should create access key")
    void shouldCreateAccessKey() {
        //Arrange
        CreateOrUpdateKey request = CreateOrUpdateKey.builder("test")
                .withBucketCreation()
                .expiresAt(Instant.now().plus(3, ChronoUnit.MINUTES))
                .build();

        //Act
        KeyInfo response = client.createKey(request);

        //Assert
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.expiration()).isBefore(Instant.now().plus(3, ChronoUnit.MINUTES));
        Assertions.assertThat(response.name()).isEqualTo("test");
    }

    @Test
    @DisplayName("Should delete key")
    void shouldDeleteKey() {
        //Arrange
        CreateOrUpdateKey request = CreateOrUpdateKey.builder("test")
                .withBucketCreation()
                .expiresAt(Instant.now().plus(3, ChronoUnit.MINUTES))
                .build();
        KeyInfo keyInfo = client.createKey(request);

        //Act
        boolean response = client.deleteKey(keyInfo.accessKeyId());
        Assertions.assertThat(response).isTrue();

    }

    @Test
    @DisplayName("Should get key info")
    void  shouldGetKeyInfo() {
        //Arrange
        CreateOrUpdateKey request = CreateOrUpdateKey.builder("test")
                .withBucketCreation()
                .expiresAt(Instant.now().plus(3, ChronoUnit.MINUTES))
                .build();
        KeyInfo keyInfo = client.createKey(request);

        //Act
        KeyInfo response = client.getKeyInfo(keyInfo.accessKeyId(), true);
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.accessKeyId()).isEqualTo(keyInfo.accessKeyId());
    }

    @Test
    @DisplayName("Should import key")
    void shouldImportKey() {
        // Arrange
        String hex = UUID.randomUUID().toString().replace("-", "").substring(0, 24).toLowerCase();
        String accessKeyId = "GK" + hex;
        String secretAccessKey = UUID.randomUUID().toString().replace("-", "") +
                UUID.randomUUID().toString().replace("-", "");

        ImportKey importKey = new ImportKey(accessKeyId, "test", secretAccessKey);

        // Act
        KeyInfo response = client.importKey(importKey);

        // Assert
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.accessKeyId()).isEqualTo(accessKeyId);
    }


    @Test
    @DisplayName("Should get all keys")
    void shouldGetAllKeys(){
        //Arrange
        CreateOrUpdateKey request = CreateOrUpdateKey.builder("test")
                .withBucketCreation()
                .expiresAt(Instant.now().plus(3, ChronoUnit.MINUTES))
                .build();
        client.createKey(request);

        //Act
        List<ListKeysResponse> response = client.getAllKeys();

        //Assert
        Assertions.assertThat(response).isNotNull();

    }

    @Test
    @DisplayName("Should update key")
    void shouldUpdateKey(){
        //Arrange
       CreateOrUpdateKey updateKey = CreateOrUpdateKey.builder("test-update")
                .withBucketCreation()
                .expiresAt(Instant.now().plus(3, ChronoUnit.MINUTES))
                .build();
        CreateOrUpdateKey createOrUpdateKey = CreateOrUpdateKey.builder("test")
                .withBucketCreation()
                .expiresAt(Instant.now().plus(3, ChronoUnit.MINUTES))
                .build();
        KeyInfo keyInfo = client.createKey(createOrUpdateKey);

        //Act
        KeyInfo response = client.updateKey(keyInfo.accessKeyId(), updateKey);

        //Assert
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.accessKeyId()).isEqualTo(keyInfo.accessKeyId());
        Assertions.assertThat(response.name()).isEqualTo("test-update");
    }


}
