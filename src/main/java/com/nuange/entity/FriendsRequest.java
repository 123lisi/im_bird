package com.nuange.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (FriendsRequest)实体类
 *
 * @author makejava
 * @since 2020-10-15 16:36:37
 */
public class FriendsRequest implements Serializable {
    private static final long serialVersionUID = 665960861669624143L;
    
    private String id;
    
    private String sendUserId;
    
    private String acceptUserId;
    /**
    * 发送请求的事件
    */
    private Date requestDateTime;


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

    public Date getRequestDateTime() {
        return requestDateTime;
    }

    public void setRequestDateTime(Date requestDateTime) {
        this.requestDateTime = requestDateTime;
    }

}