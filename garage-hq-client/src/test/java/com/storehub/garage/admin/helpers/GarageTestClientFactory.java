package com.storehub.garage.admin.helpers;

import com.storehub.garage.admin.GarageAdminClient;
import com.storehub.garage.admin.config.GarageAdminClientConfig;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GarageTestClientFactory {

    public static <T> T createClient(Class<T> clientClass) {
        GarageAdminClientConfig config = new GarageAdminClientConfig(TestConfig.getAdminUrl(), TestConfig.getAdminToken());
        GarageAdminClient adminClient = new GarageAdminClient.Builder(config).build();
        return adminClient.getClient(clientClass);
    }
}
