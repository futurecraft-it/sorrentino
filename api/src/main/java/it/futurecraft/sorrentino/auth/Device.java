package it.futurecraft.sorrentino.auth;

import com.squareup.moshi.Json;

public record Device(
        @Json(name = "device_code") String deviceCode,
        @Json(name = "user_code") String userCode,
        int interval,
        @Json(name = "expires_in") int expiresIn,
        @Json(name = "verification_uri") String verificationUri
) {
}
