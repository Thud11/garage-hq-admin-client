package com.storehub.garage.admin.modules.keys.internal;

import com.storehub.garage.admin.modules.keys.config.KeyApiPath;
import com.storehub.garage.admin.modules.keys.models.request.CreateOrUpdateKey;
import com.storehub.garage.admin.modules.keys.models.request.ImportKey;
import com.storehub.garage.admin.modules.keys.models.response.KeyInfo;
import com.storehub.garage.admin.modules.keys.models.response.ListKeysResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

public interface KeysApi {


    @POST(KeyApiPath.CREATE_KEY)
    Call<KeyInfo> createKey(@Body CreateOrUpdateKey request);

    @POST(KeyApiPath.DELETE_KEY)
    Call<Void> deleteKey(@Query("id") String id);

    @GET(KeyApiPath.GET_KEY_INFO)
    Call<KeyInfo> getKeyInfo(@Query("id")String id, @Query("showSecretKey") boolean returnSecretKey);

    @POST(KeyApiPath.IMPORT_KEYS)
    Call<KeyInfo> importKey(@Body ImportKey importKey);

    @GET(KeyApiPath.LIST_KEYS)
    Call<List<ListKeysResponse>> getAllKeys();

    @POST(KeyApiPath.UPDATE_KEY)
    Call<KeyInfo> updateKey(@Query("id") String id, @Body CreateOrUpdateKey updateKey);
}
