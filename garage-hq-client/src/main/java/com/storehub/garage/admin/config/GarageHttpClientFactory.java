package com.storehub.garage.admin.config;

import lombok.experimental.UtilityClass;
import okhttp3.OkHttpClient;
import okhttp3.Request;

@UtilityClass
public class GarageHttpClientFactory {

    public static OkHttpClient create(GarageAdminClientConfig config) {
        return new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request originalRequest = chain.request();
                    Request newRequest = originalRequest.newBuilder()
                            .header("Authorization", "Bearer " + config.masterToken())
                            .build();
                    return chain.proceed(newRequest);
                })
                .build();
    }
}