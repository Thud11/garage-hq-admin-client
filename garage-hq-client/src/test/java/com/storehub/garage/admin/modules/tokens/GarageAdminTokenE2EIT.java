package com.storehub.garage.admin.modules.tokens;

import com.storehub.garage.admin.GarageAdminClient;
import com.storehub.garage.admin.config.GarageAdminClientConfig;
import com.storehub.garage.admin.helpers.GarageTestClientFactory;
import com.storehub.garage.admin.helpers.TestConfig;
import com.storehub.garage.admin.modules.specials.GarageSpecialClient;
import com.storehub.garage.admin.modules.tokens.model.Scopes;
import com.storehub.garage.admin.modules.tokens.model.request.CreateOrUpdateAdminToken;
import com.storehub.garage.admin.modules.tokens.model.response.AdminTokenCreated;
import com.storehub.garage.admin.modules.tokens.model.response.AdminTokenInfo;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@DisplayName("Admin Token API Integration Tests")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GarageAdminTokenE2EIT {

    private static final String TOKEN_NAME_PREFIX = "test-token-";

    private static GarageAdminTokenClient client;
    private final List<String> createdTokenIds = new ArrayList<>();

    @BeforeAll
    static void setupClient() {
        client = GarageTestClientFactory.createClient(GarageAdminTokenClient.class);
    }

    @AfterAll
    void cleanupTokens() {
        log.info("Cleaning up {} created test tokens...", createdTokenIds.size());
        createdTokenIds.forEach(tokenId -> {
            try {
                client.deleteAdminToken(tokenId);
                log.info("Successfully deleted token: {}", tokenId);
            } catch (Exception e) {
                log.error("Failed to delete token: {}", tokenId, e);
            }
        });
        createdTokenIds.clear();
    }

    @Test
    @DisplayName("Should create admin token")
    void shouldCreateAdminToken() {
        // Act
        AdminTokenCreated tokenCreated = createTestToken(List.of(Scopes.values()));

        // Assert
        Assertions.assertThat(tokenCreated).isNotNull();
        Assertions.assertThat(tokenCreated.secretToken()).isNotEmpty();
        Assertions.assertThat(tokenCreated.name()).startsWith(TOKEN_NAME_PREFIX);
    }

    @Test
    @DisplayName("Should get token info by ID")
    void shouldGetTokenInfoById() {
        // Arrange
        AdminTokenCreated tokenForTest = createTestToken(List.of(Scopes.values()));

        // Act
        AdminTokenInfo response = client.getAdminTokenInfo(tokenForTest.id());

        // Assert
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.id()).isEqualTo(tokenForTest.id());
    }

    @Test
    @DisplayName("Should delete an admin token")
    void shouldDeleteAnAdminToken() {
        // Arrange
        AdminTokenCreated tokenToDelete = createTestToken(List.of(Scopes.LIST_BUCKETS));

        // Act
        boolean response = client.deleteAdminToken(tokenToDelete.id());
        createdTokenIds.remove(tokenToDelete.id());

        // Assert
        Assertions.assertThat(response).isTrue();
    }

    @Test
    @DisplayName("Should update an admin token")
    void shouldUpdateAnAdminToken() {
        // Arrange
        AdminTokenCreated tokenForTest = createTestToken(List.of(Scopes.values()));
        String updatedName = "updated-token-" + UUID.randomUUID();
        CreateOrUpdateAdminToken updateRequest = CreateOrUpdateAdminToken.thatNeverExpires(updatedName, List.of(Scopes.LIST_BUCKETS));

        // Act
        AdminTokenInfo response = client.updateAdminToken(tokenForTest.id(), updateRequest);

        // Assert
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.id()).isEqualTo(tokenForTest.id());
        Assertions.assertThat(response.name()).isEqualTo(updatedName);
    }

    @Test
    @DisplayName("Should get the list of all tokens")
    void shouldGetTokenList() {
        // Arrange
        AdminTokenCreated tokenForTest = createTestToken(List.of(Scopes.values()));

        // Act
        List<AdminTokenInfo> response = client.getAdminTokenList();

        // Assert
        Assertions.assertThat(response)
                .isNotEmpty()
                .extracting(AdminTokenInfo::id)
                .contains(tokenForTest.id());
    }

    @Test
    @DisplayName("Should get current token info")
    void shouldGetCurrentAdminTokenInfo() {
        // Act
        AdminTokenInfo response = client.getCurrentTokenInfo();

        // Assert
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.name()).isNotEmpty();
    }

    private AdminTokenCreated createTestToken(List<Scopes> scopes) {
        AdminTokenCreated token = client.createAdminToken(
                CreateOrUpdateAdminToken.thatExpiresAt(
                        GarageAdminTokenE2EIT.TOKEN_NAME_PREFIX + UUID.randomUUID(),
                        Instant.now().plus(5, ChronoUnit.MINUTES),
                        scopes
                )
        );
        createdTokenIds.add(token.id());
        return token;
    }
}
