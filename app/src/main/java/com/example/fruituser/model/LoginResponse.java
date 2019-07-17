package com.example.fruituser.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("sessionKey")
    @Expose
    private String sessionKey;

    @SerializedName("profile")
    @Expose
    private LoginUserProfile profile;

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public LoginUserProfile getProfile() {
        return profile;
    }

    public void setProfile(LoginUserProfile profile) {
        this.profile = profile;
    }
}
