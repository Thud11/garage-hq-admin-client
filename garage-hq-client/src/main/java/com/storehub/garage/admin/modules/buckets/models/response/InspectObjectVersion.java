package com.storehub.garage.admin.modules.buckets.models.response;

import java.time.Instant;
import java.util.List;

public record InspectObjectVersion(
        boolean aborted,
        List<InspectObjectBlock> blocks,
        boolean deleteMarker,
        boolean encrypted,
        String etag,
        List<String> headers,
        boolean inline,
        int size,
        Instant timestamp,
        boolean uploading,
        String uuid


) {
}
