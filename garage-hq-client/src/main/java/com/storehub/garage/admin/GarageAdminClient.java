package com.storehub.garage.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.storehub.garage.admin.config.*;
import com.storehub.garage.admin.modules.buckets.GarageBucketClient;
import com.storehub.garage.admin.modules.buckets.internal.BucketApi;
import com.storehub.garage.admin.modules.keys.GarageAccessKeyClient;
import com.storehub.garage.admin.modules.keys.internal.KeysApi;
import com.storehub.garage.admin.modules.specials.GarageSpecialClient;
import com.storehub.garage.admin.modules.specials.internal.SpecialApi;
import com.storehub.garage.admin.modules.tokens.GarageAdminTokenClient;
import com.storehub.garage.admin.modules.tokens.internal.AdminTokenApi;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import java.util.LinkedHashMap;
import java.util.Map;

public final class GarageAdminClient {

    private final Map<Class<?>, Object> clientRegistry;

    private GarageAdminClient(Map<Class<?>, Object> clientRegistry) {
        this.clientRegistry = Map.copyOf(clientRegistry);
    }

    @SuppressWarnings("unchecked")
    public <T> T getClient(Class<T> clientClass) {
        T client = (T) clientRegistry.get(clientClass);
        if (client == null) {
            throw new IllegalArgumentException("Client not registered: " + clientClass.getName());
        }
        return client;
    }

    @SuppressWarnings("unused")
    public GarageSpecialClient specials() {
        return getClient(GarageSpecialClient.class);
    }

    @SuppressWarnings("unused")
    public GarageAdminTokenClient adminTokens() {
        return getClient(GarageAdminTokenClient.class);
    }

    @SuppressWarnings("unused")
    public GarageBucketClient buckets() {
        return getClient(GarageBucketClient.class);
    }

    @SuppressWarnings("unused")
    public  GarageAccessKeyClient accessKeys() {
        return getClient(GarageAccessKeyClient.class);
    }


    public static class Builder {
        private final GarageAdminClientConfig config;

        private final Map<Class<?>, ClientFactory<?>> clientFactoryRegistry = new LinkedHashMap<>();

        public Builder(GarageAdminClientConfig config) {
            this.config = config;
            registerDefaultClients();
        }

        private void registerDefaultClients() {
            registerClient(GarageSpecialClient.class, retrofit -> new GarageSpecialClient(retrofit.create(SpecialApi.class)));
            registerClient(GarageAdminTokenClient.class, retrofit -> new GarageAdminTokenClient(retrofit.create(AdminTokenApi.class)));
            registerClient(GarageBucketClient.class, retrofit -> new GarageBucketClient(retrofit.create(BucketApi.class)));
            registerClient(GarageAccessKeyClient.class, retrofit -> new GarageAccessKeyClient(retrofit.create(KeysApi.class)));

        }

        public <T> void registerClient(Class<T> clientClass, ClientFactory<T> factory) {
            this.clientFactoryRegistry.put(clientClass, factory);
        }

        public GarageAdminClient build() {
            OkHttpClient httpClient = GarageHttpClientFactory.create(config);
            ObjectMapper objectMapper = GarageObjectMapperFactory.create();
            Retrofit retrofit = GarageRetrofitFactory.create(config, httpClient, objectMapper);

            Map<Class<?>, Object> builtClients = new LinkedHashMap<>();
            for (Map.Entry<Class<?>, ClientFactory<?>> entry : clientFactoryRegistry.entrySet()) {
                builtClients.put(entry.getKey(), entry.getValue().create(retrofit));
            }

            if (builtClients.isEmpty()) {
                throw new IllegalStateException("No client factories registered.");
            }

            return new GarageAdminClient(builtClients);
        }
    }
}
