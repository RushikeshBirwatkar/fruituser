package com.example.fruituser.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyOtpRequest {
    @SerializedName("userId")
    @Expose
    private  String userId;

    @SerializedName("otp")
    @Expose
    private String otp;

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
}
