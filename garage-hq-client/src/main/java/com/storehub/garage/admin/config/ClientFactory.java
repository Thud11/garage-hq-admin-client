package com.storehub.garage.admin.config;

import retrofit2.Retrofit;

@FunctionalInterface
public interface ClientFactory<T> {
    T create(Retrofit retrofit);
}
