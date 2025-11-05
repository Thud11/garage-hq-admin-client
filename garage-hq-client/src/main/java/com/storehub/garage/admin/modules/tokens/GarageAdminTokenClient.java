package com.storehub.garage.admin.modules.tokens;


import com.storehub.garage.admin.executor.GarageApiCallExecutor;
import com.storehub.garage.admin.modules.tokens.internal.AdminTokenApi;
import com.storehub.garage.admin.modules.tokens.model.request.CreateOrUpdateAdminToken;
import com.storehub.garage.admin.modules.tokens.model.response.AdminTokenCreated;
import com.storehub.garage.admin.modules.tokens.model.response.AdminTokenInfo;

import java.util.List;

@SuppressWarnings("ClassCanBeRecord")
public class GarageAdminTokenClient {

    private final AdminTokenApi api;

    public GarageAdminTokenClient(AdminTokenApi adminTokenApi) {
        this.api = adminTokenApi;
    }

    public AdminTokenCreated createAdminToken(CreateOrUpdateAdminToken createOrUpdateAdminToken) {
        return GarageApiCallExecutor.execute(() -> api.createAdminToken(createOrUpdateAdminToken).execute()).body();
    }

    public boolean deleteAdminToken(String id){
        return GarageApiCallExecutor.execute(() -> api.deleteAdminToken(id).execute()).isSuccessful();
    }

    public AdminTokenInfo getAdminTokenInfo(String id){
        return GarageApiCallExecutor.execute(() -> api.getAdminTokenById(id).execute()).body();
    }

    public AdminTokenInfo getCurrentTokenInfo(){
        return GarageApiCallExecutor.execute(() -> api.getCurrentAdminTokenInfo().execute()).body();
    }

    public List<AdminTokenInfo> getAdminTokenList(){
        return GarageApiCallExecutor.execute(() -> api.getAdminTokenList().execute()).body();
    }

    public AdminTokenInfo updateAdminToken(String id, CreateOrUpdateAdminToken createOrUpdateAdminToken){
        return GarageApiCallExecutor.execute(() -> api.updateAdminToken(id, createOrUpdateAdminToken).execute()).body();
    }

}
