package com.storehub.garage.admin.config;

public record GarageAdminClientConfig(String adminApiUrl, String masterToken) {
    public GarageAdminClientConfig {
        if (adminApiUrl == null || adminApiUrl.isBlank()) {
            throw new IllegalArgumentException("URL cannot be null or empty");
        }
        if (masterToken == null || masterToken.isBlank()) {
            throw new IllegalArgumentException("Token cannot be null or empty");
        }
    }
}