package com.example.fruituser.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FileDto {
    @SerializedName("originalFileName")
    @Expose
    private String originalFileName;

    @SerializedName("fileId")
    @Expose
    private String fileId;

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }


    @Override
    public String toString() {
        return "Original File name "+originalFileName+"File id "+fileId;
    }
}
