package com.storehub.garage.admin.starter.config;

import com.storehub.garage.admin.GarageAdminClient;
import com.storehub.garage.admin.config.GarageAdminClientConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(GarageAdminClientProperties.class)
@ConditionalOnProperty(prefix = "garage.admin", name = {"url", "master-token"})
public class GarageAdminClientAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public GarageAdminClientConfig garageAdminClientConfig(GarageAdminClientProperties properties) {
        return new GarageAdminClientConfig(properties.getUrl(), properties.getMasterToken());
    }

    @Bean
    @ConditionalOnMissingBean
    public GarageAdminClient garageAdminClient(GarageAdminClientConfig config) {
        return new GarageAdminClient.Builder(config).build();
    }
}
