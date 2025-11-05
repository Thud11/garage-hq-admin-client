package com.storehub.garage.admin.modules.buckets.models.response;

public record InspectObjectBlock(
        String hash,
        int offset,
        int partNumber,
        int size
) {
}
