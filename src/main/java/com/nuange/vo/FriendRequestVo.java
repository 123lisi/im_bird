package com.nuange.vo;

public class FriendRequestVo {
    private String sendUserId;
    private String sendUserName;
    private String sendFaceImage;
    private String sendNikename;

    public String getSendNikename() {
        return sendNikename;
    }

    public void setSendNikename(String sendNikename) {
        this.sendNikename = sendNikename;
    }

    public String getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
    }

    public String getSendFaceImage() {
        return sendFaceImage;
    }

    public void setSendFaceImage(String sendFaceImage) {
        this.sendFaceImage = sendFaceImage;
    }

    public String getSendNikeName() {
        return sendNikeName;
    }

    public void setSendNikeName(String sendNikeName) {
        this.sendNikeName = sendNikeName;
    }

    private String sendNikeName;

    public String getSendUserName() {
        return sendUserName;
    }

    public void setSendUserName(String sendUserName) {
        this.sendUserName = sendUserName;
    }
}
