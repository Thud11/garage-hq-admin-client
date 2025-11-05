package com.storehub.garage.admin.modules.special;

import com.storehub.garage.admin.helpers.GarageTestClientFactory;
import com.storehub.garage.admin.modules.specials.GarageSpecialClient;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
class GarageSpecialClientE2EIT {

    private static GarageSpecialClient client;

    @BeforeAll
    static void setupClient() {
        client = GarageTestClientFactory.createClient(GarageSpecialClient.class);
    }

    @Test
    @DisplayName("Health check")
    void checkHealthRealServer() {
        final boolean isHealthy = client.checkHealth();
        Assertions.assertThat(isHealthy).isTrue();
    }

    @Test
    @DisplayName("Domain check")
    void checkDomainRealServer() {
        final boolean domainCheckResult = client.checkDomain("localhost");
        Assertions.assertThat(domainCheckResult).isFalse();
    }

    @Test
    @DisplayName("Get Metrics")
    void getMetricsRealServer() {
        final String metrics = client.getMetrics();
        Assertions.assertThat(metrics).isNotEmpty();
    }
}
