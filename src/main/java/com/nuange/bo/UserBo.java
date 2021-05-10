package com.nuange.bo;

public class UserBo {
    private String userId;
    private String faceData;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFaceData() {
        return faceData;
    }

    public void setFaceData(String faceData) {
        this.faceData = faceData;
    }

    @Override
    public String toString() {
        return "UserBo{" +
                "userId='" + userId + '\'' +
                ", faceData='" + faceData + '\'' +
                '}';
    }
}
