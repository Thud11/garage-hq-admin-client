package com.storehub.garage.admin.modules.buckets.models.request;

public record CleanupIncompleteUploads(String bucketId, int olderThanSecs) {
}
