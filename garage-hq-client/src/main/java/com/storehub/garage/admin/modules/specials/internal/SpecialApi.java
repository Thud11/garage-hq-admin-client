package com.storehub.garage.admin.modules.specials.internal;

import com.storehub.garage.admin.modules.specials.config.SpecialApiPath;
import com.storehub.garage.admin.modules.specials.config.SpecialApiQuery;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SpecialApi {

    @GET(SpecialApiPath.CHECK_DOMAIN)
    Call<Void> checkDomain(@Query(SpecialApiQuery.DOMAIN) String domain);

    @GET(SpecialApiPath.CHECK_HEALTH)
    Call<Void> checkHealth();

    @GET(SpecialApiPath.GET_METRICS)
    Call<String> getMetrics();
}
