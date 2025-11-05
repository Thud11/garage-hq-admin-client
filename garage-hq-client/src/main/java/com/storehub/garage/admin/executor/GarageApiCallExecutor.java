package com.storehub.garage.admin.executor;

import com.storehub.garage.admin.exception.*;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import retrofit2.Response;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;

@UtilityClass
public class GarageApiCallExecutor {

    public <T> Response<T> execute(Callable<Response<T>> apiCall) {
        return execute(apiCall, Set.of());
    }

    public <T> Response<T> execute(Callable<Response<T>> apiCall, Set<Integer> allowedNonStandardCodes) {
        try {
            Response<T> response = apiCall.call();
            handleErrorResponse(response, allowedNonStandardCodes);
            return response;
        } catch (IOException e) {
            throw new ApiConnectivityException("Failed to connect to API", e);
        } catch (ApiServerException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiConnectivityException("An unexpected error occurred", e);
        }
    }
    @SneakyThrows
    private <T> void handleErrorResponse(Response<T> response, Set<Integer> allowedCodes) {
        if (response.isSuccessful() || allowedCodes.contains(response.code())) {
            return;
        }

        throw switch (response.code()) {
            case 401, 403 -> new ApiAuthorizationException("Authorization failed. Check token.");
            case 404 -> new ResourceNotFoundException("Resource not found.");
            default -> new ApiServerException("API server error.", response.code(), Objects.requireNonNull(response.errorBody()).string());
        };
    }
}
