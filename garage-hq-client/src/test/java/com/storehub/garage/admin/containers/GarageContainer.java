package com.storehub.garage.admin.containers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

@Testcontainers
public interface GarageContainer {

    int GARAGE_API_PORT = 3900;
    int GARAGE_WEB_PORT = 3902;
    int GARAGE_ADMIN_PORT = 3903;

    Logger GARAGE_LOGGER = LoggerFactory.getLogger(GarageContainer.class);

    static void configureLogging() {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "warn");
    }

    @Container
    GenericContainer<?> GARAGE_CONTAINER =
            new GenericContainer<>(DockerImageName.parse("registry.storehub.com.ua/garage-test:it-must-work"))
                    .withExposedPorts(GARAGE_API_PORT, GARAGE_WEB_PORT, GARAGE_ADMIN_PORT)
                    .withLogConsumer(new Slf4jLogConsumer(GARAGE_LOGGER))
                    .waitingFor(
                            Wait.forHttp("/health")
                                    .forPort(GARAGE_ADMIN_PORT)
                                    .forStatusCode(200)
                                    .withStartupTimeout(Duration.ofSeconds(60))
                    );


    default String getGarageApiUrl() {
        return String.format("http://%s:%d",
                GARAGE_CONTAINER.getHost(),
                GARAGE_CONTAINER.getMappedPort(GARAGE_API_PORT));
    }

    default String getGarageAdminUrl() {
        return String.format("http://%s:%d",
                GARAGE_CONTAINER.getHost(),
                GARAGE_CONTAINER.getMappedPort(GARAGE_ADMIN_PORT));
    }
}
