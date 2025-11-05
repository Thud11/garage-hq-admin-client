package com.storehub.garage.admin.modules.specials;

import com.storehub.garage.admin.executor.GarageApiCallExecutor;
import com.storehub.garage.admin.modules.specials.internal.SpecialApi;
import retrofit2.Response;

import java.util.Set;

@SuppressWarnings("ClassCanBeRecord")
public class GarageSpecialClient {

    private final SpecialApi api;

    public GarageSpecialClient(SpecialApi api) {
        this.api = api;
    }

    public boolean checkHealth() {
        return GarageApiCallExecutor.execute(() -> api.checkHealth().execute()).isSuccessful();
    }

    public boolean checkDomain(String domain) {
        Response<?> response = GarageApiCallExecutor.execute(
                () -> api.checkDomain(domain).execute(),
                Set.of(400)
        );
        return response.isSuccessful();
    }

    public String getMetrics() {
        return GarageApiCallExecutor.execute(() -> api.getMetrics().execute()).body();
    }


}
