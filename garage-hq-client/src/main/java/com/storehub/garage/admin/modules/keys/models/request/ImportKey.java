package com.storehub.garage.admin.modules.keys.models.request;

public record ImportKey(
        String accessKeyId,
        String name,
        String secretAccessKey

) {
}
