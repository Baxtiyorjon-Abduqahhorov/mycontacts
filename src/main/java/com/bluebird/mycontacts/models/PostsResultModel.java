package com.bluebird.mycontacts.models;

import com.bluebird.mycontacts.entities.UserInfo;

import javax.persistence.Column;
import java.io.Serializable;
import java.sql.Timestamp;

public class PostsResultModel implements Serializable {

    private Long id;

    private String picture;

    private String caption;

    private Timestamp createdDate;

    @Column()
    private Timestamp editedDate;

    private UserInfo userInfo;

    public PostsResultModel(Long id, String picture, String caption, Timestamp createdDate, Timestamp editedDate, UserInfo userInfo) {
        this.id = id;
        this.picture = picture;
        this.caption = caption;
        this.createdDate = createdDate;
        this.editedDate = editedDate;
        this.userInfo = userInfo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getEditedDate() {
        return editedDate;
    }

    public void setEditedDate(Timestamp editedDate) {
        this.editedDate = editedDate;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
