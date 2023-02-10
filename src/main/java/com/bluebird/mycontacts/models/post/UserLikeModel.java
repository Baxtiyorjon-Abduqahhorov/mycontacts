package com.bluebird.mycontacts.models.post;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class UserLikeModel implements Serializable {

    @JsonIgnore
    private Long id;

    private String username;

    private String pro_pic;

    private String bio;

    public UserLikeModel(Long id, String username, String pro_pic, String bio) {
        this.id = id;
        this.username = username;
        this.pro_pic = pro_pic;
        this.bio = bio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPro_pic() {
        return pro_pic;
    }

    public void setPro_pic(String pro_pic) {
        this.pro_pic = pro_pic;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
