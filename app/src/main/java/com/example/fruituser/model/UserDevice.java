package com.example.fruituser.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDevice {

    @SerializedName("active")
    @Expose
    private Boolean active;

    @SerializedName("createdBy")
    @Expose
    private String createdBy;

    @SerializedName("updatedBy")
    @Expose
    private String updatedBy;

    @SerializedName("createdAt")
    @Expose
    private Long createdAt;

    @SerializedName("updatedAt")
    @Expose
    private Long updatedAt;

    @SerializedName("deleted")
    @Expose
    private Boolean deleted;

    @SerializedName("userDeviceId")
    @Expose
    private String userDeviceId;

    @SerializedName("userId")
    @Expose
    private String userId;

    @SerializedName("deviceUid")
    @Expose
    private String deviceUid;

    @SerializedName("deviceToken")
    @Expose
    private String deviceToken;

    @SerializedName("deviceName")
    @Expose
    private String deviceName;

    @SerializedName("deviceType")
    @Expose
    private String deviceType;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getUserDeviceId() {
        return userDeviceId;
    }

    public void setUserDeviceId(String userDeviceId) {
        this.userDeviceId = userDeviceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeviceUid() {
        return deviceUid;
    }

    public void setDeviceUid(String deviceUid) {
        this.deviceUid = deviceUid;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
}
