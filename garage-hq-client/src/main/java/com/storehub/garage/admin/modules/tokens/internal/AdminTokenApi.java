package com.storehub.garage.admin.modules.tokens.internal;

import com.storehub.garage.admin.modules.tokens.config.AdminTokenApiPath;
import com.storehub.garage.admin.modules.tokens.model.request.CreateOrUpdateAdminToken;
import com.storehub.garage.admin.modules.tokens.model.response.AdminTokenCreated;
import com.storehub.garage.admin.modules.tokens.model.response.AdminTokenInfo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

public interface AdminTokenApi {
    @POST(AdminTokenApiPath.CREATE_ADMIN_TOKEN)
    Call<AdminTokenCreated> createAdminToken(@Body CreateOrUpdateAdminToken createOrUpdateAdminToken);

    @POST(AdminTokenApiPath.DELETE_ADMIN_TOKEN)
    Call<Void> deleteAdminToken(@Query("id") String id);

    @GET(AdminTokenApiPath.GET_ADMIN_TOKEN_INFO)
    Call<AdminTokenInfo> getAdminTokenById(@Query("id") String tokenId);

    @GET(AdminTokenApiPath.GET_CURRENT_ADMIN_TOKEN_INFO)
    Call<AdminTokenInfo> getCurrentAdminTokenInfo();

    @GET(AdminTokenApiPath.LIST_ALL_ADMIN_TOKENS)
    Call<List<AdminTokenInfo>> getAdminTokenList();

    @POST(AdminTokenApiPath.UPDATE_ADMIN_TOKEN)
    Call<AdminTokenInfo> updateAdminToken(@Query("id") String id, @Body CreateOrUpdateAdminToken updateAdminToken);
}
