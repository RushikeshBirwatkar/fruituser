package com.example.fruituser.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserForgotPasswordRequest {
    @SerializedName("userId")
    @Expose
    private String userId;

    @SerializedName("otp")
    @Expose
    private String otp;

    @SerializedName("newPassword")
    @Expose
    private String newPassword;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
