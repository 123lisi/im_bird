package com.nuange.webscoket;

import java.io.Serializable;

public class ChatMsg implements Serializable {
    private String senderId; //发送者id
    private String receiverId; //接受者
    private String msg; // 聊天内容
    private String msgId; //消息的ID

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String sendId) {
        this.senderId = sendId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    @Override
    public String toString() {
        return "ChatMsg{" +
                "senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", msg='" + msg + '\'' +
                ", msgId='" + msgId + '\'' +
                '}';
    }
}
