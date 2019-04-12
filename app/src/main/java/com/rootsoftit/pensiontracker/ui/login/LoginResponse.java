package com.rootsoftit.pensiontracker.ui.login;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("expires_at")
    private String expiresAt;

    @SerializedName("token_type")
    private String tokenType;

    public String getAccessToken() {
        return accessToken;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public String getTokenType() {
        return tokenType;
    }
}