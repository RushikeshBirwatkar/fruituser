package com.example.fruituser.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequest {


    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("deviceInfo")
    @Expose
    private DeviceDetails deviceInfo;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public DeviceDetails getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceDetails deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
}
