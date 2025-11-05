package com.storehub.garage.admin.modules.keys;

import com.storehub.garage.admin.executor.GarageApiCallExecutor;
import com.storehub.garage.admin.modules.keys.internal.KeysApi;
import com.storehub.garage.admin.modules.keys.models.request.CreateOrUpdateKey;
import com.storehub.garage.admin.modules.keys.models.request.ImportKey;
import com.storehub.garage.admin.modules.keys.models.response.KeyInfo;
import com.storehub.garage.admin.modules.keys.models.response.ListKeysResponse;

import java.util.List;

public class GarageAccessKeyClient {
    
    private final KeysApi api;
    
    public GarageAccessKeyClient(KeysApi api) {
        this.api = api;
    }
    
    public KeyInfo createKey(CreateOrUpdateKey request) {
        return GarageApiCallExecutor.execute(() -> api.createKey(request).execute()).body();
    }

    public boolean deleteKey(String id) {
        return GarageApiCallExecutor.execute(() -> api.deleteKey(id).execute()).isSuccessful();
    }

    public KeyInfo getKeyInfo(String id, boolean returnSecretKey) {
        return GarageApiCallExecutor.execute(() -> api.getKeyInfo(id, returnSecretKey).execute()).body();
    }

    public KeyInfo importKey(ImportKey importKey) {
        return GarageApiCallExecutor.execute(()-> api.importKey(importKey).execute()).body();
    }

    public List<ListKeysResponse> getAllKeys() {
        return GarageApiCallExecutor.execute(() -> api.getAllKeys().execute()).body();
    }

    public KeyInfo updateKey(String id, CreateOrUpdateKey updateKey) {
        return GarageApiCallExecutor.execute(() -> api.updateKey(id, updateKey).execute()).body();
    }
}
