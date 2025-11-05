package com.storehub.garage.admin.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Objects;
import java.util.Properties;

public final class TestConfig {

    private static final String CONFIG_FILE = "config.properties";

    private final String adminToken;
    private final String adminUrl;

    private static TestConfig instance;

    private TestConfig() {
        Properties props = new Properties();
        try (InputStream is = TestConfig.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (is == null) {
                throw new IllegalStateException("Config file not found in classpath: " + CONFIG_FILE);
            }
            props.load(is);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to load config file: " + CONFIG_FILE, e);
        }

        this.adminToken = Objects.requireNonNull(
                props.getProperty("admin.token"),
                "'admin.token' is missing in " + CONFIG_FILE
        );
        this.adminUrl = Objects.requireNonNull(
                props.getProperty("admin.url"),
                "'admin.url' is missing in " + CONFIG_FILE
        );
    }

    private static synchronized TestConfig getInstance() {
        if (instance == null) {
            instance = new TestConfig();
        }
        return instance;
    }

    public static String getAdminToken() {
        return getInstance().adminToken;
    }

    public static String getAdminUrl() {
        return getInstance().adminUrl;
    }
}
