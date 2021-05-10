package com.nuange.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (ChatMsg)实体类
 *
 * @author makejava
 * @since 2020-09-23 18:09:23
 */
public class ChatMsg implements Serializable {
    private static final long serialVersionUID = 794134234910797541L;
    
    private String id;
    
    private String sendUserId;
    
    private String acceptUserId;
    
    private String msg;
    /**
    * 消息是否签收状态
1：签收
0：未签收

    */
    private Integer signFlag;
    /**
    * 发送请求的事件
    */
    private Date createTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
    }

    public String getAcceptUserId() {
        return acceptUserId;
    }

    public void setAcceptUserId(String acceptUserId) {
        this.acceptUserId = acceptUserId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getSignFlag() {
        return signFlag;
    }

    public void setSignFlag(Integer signFlag) {
        this.signFlag = signFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ChatMsg{" +
                "id='" + id + '\'' +
                ", sendUserId='" + sendUserId + '\'' +
                ", acceptUserId='" + acceptUserId + '\'' +
                ", msg='" + msg + '\'' +
                ", signFlag=" + signFlag +
                ", createTime=" + createTime +
                '}';
    }
}