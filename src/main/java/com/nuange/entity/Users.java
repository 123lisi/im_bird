package com.nuange.entity;

import java.io.Serializable;

/**
 * (Users)实体类
 *
 * @author makejava
 * @since 2020-09-23 18:38:15
 */
public class Users implements Serializable {
    private static final long serialVersionUID = -55650484756043570L;
    
    private String id;
    /**
    * 用户名，账号，慕信号
    */
    private String username;
    /**
    * 密码
    */
    private String password;
    /**
    * 我的头像，如果没有默认给一张
    */
    private String faceImage;
    
    private String faceImageBig;
    /**
    * 昵称
    */
    private String nickname;
    /**
    * 新用户注册后默认后台生成二维码，并且上传到fastdfs
    */
    private String qrcode;
    
    private String cid;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFaceImage() {
        return faceImage;
    }

    public void setFaceImage(String faceImage) {
        this.faceImage = faceImage;
    }

    public String getFaceImageBig() {
        return faceImageBig;
    }

    public void setFaceImageBig(String faceImageBig) {
        this.faceImageBig = faceImageBig;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", faceImage='" + faceImage + '\'' +
                ", faceImageBig='" + faceImageBig + '\'' +
                ", nickname='" + nickname + '\'' +
                ", qrcode='" + qrcode + '\'' +
                ", cid='" + cid + '\'' +
                '}';
    }
}