package com.storehub.garage.admin.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@UtilityClass
public class GarageRetrofitFactory {

    public static Retrofit create(GarageAdminClientConfig config, OkHttpClient httpClient, ObjectMapper objectMapper) {
        JacksonConverterFactory jacksonFactory = JacksonConverterFactory.create(objectMapper);
        return new Retrofit.Builder()
                .baseUrl(config.adminApiUrl())
                .client(httpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(jacksonFactory)
                .build();
    }
}