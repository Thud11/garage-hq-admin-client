package com.storehub.garage.admin.modules.tokens.config;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AdminTokenApiPath {

    public static final String CREATE_ADMIN_TOKEN = "/v2/CreateAdminToken";
    public static final String DELETE_ADMIN_TOKEN = "/v2/DeleteAdminToken";
    public static final String GET_ADMIN_TOKEN_INFO = "/v2/GetAdminTokenInfo";
    public static final String GET_CURRENT_ADMIN_TOKEN_INFO = "/v2/GetCurrentAdminTokenInfo";
    public static final String LIST_ALL_ADMIN_TOKENS = "/v2/ListAdminTokens";
    public static final String UPDATE_ADMIN_TOKEN = "/v2/UpdateAdminToken";
}
