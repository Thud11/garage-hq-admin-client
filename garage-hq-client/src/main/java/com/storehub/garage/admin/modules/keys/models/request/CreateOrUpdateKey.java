package com.storehub.garage.admin.modules.keys.models.request;

import java.time.Instant;

public record CreateOrUpdateKey(
        KeyPerm allow,
        KeyPerm deny,
        Instant expiration,
        String name,
        boolean neverExpires
) {

    public CreateOrUpdateKey {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Key name cannot be null or blank.");
        }
        if (expiration != null && neverExpires) {
            throw new IllegalArgumentException("Cannot set a specific expiration date if 'neverExpires' is true.");
        }
        if (expiration == null && !neverExpires) {
            throw new IllegalArgumentException("Must either provide an expiration date or set 'neverExpires' to true.");
        }
    }

    public static Builder builder(String name) {
        return new Builder(name);
    }

    public static final class Builder {
        private final String name;
        private boolean canCreateBuckets = false;
        private Instant expiration = null;

        private Builder(String name) {
            this.name = name;
        }

        public Builder withBucketCreation() {
            this.canCreateBuckets = true;
            return this;
        }

        public Builder expiresAt(Instant expiration) {
            this.expiration = expiration;
            return this;
        }

        public CreateOrUpdateKey build() {
            KeyPerm allowPermission = new KeyPerm(this.canCreateBuckets);
            boolean neverExpiresFlag = (this.expiration == null);

            // The validated constructor of the record is called here
            return new CreateOrUpdateKey(allowPermission, null, this.expiration, this.name, neverExpiresFlag);
        }
    }
}