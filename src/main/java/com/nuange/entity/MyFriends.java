package com.nuange.entity;

import java.io.Serializable;

/**
 * (MyFriends)实体类
 *
 * @author makejava
 * @since 2020-10-14 17:29:26
 */
public class MyFriends implements Serializable {
    private static final long serialVersionUID = 756821440811104315L;
    
    private String id;
    /**
    * 用户id
    */
    private String myUserId;
    /**
    * 用户的好友id
    */
    private String myFriendUserId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMyUserId() {
        return myUserId;
    }

    public void setMyUserId(String myUserId) {
        this.myUserId = myUserId;
    }

    public String getMyFriendUserId() {
        return myFriendUserId;
    }

    public void setMyFriendUserId(String myFriendUserId) {
        this.myFriendUserId = myFriendUserId;
    }

}