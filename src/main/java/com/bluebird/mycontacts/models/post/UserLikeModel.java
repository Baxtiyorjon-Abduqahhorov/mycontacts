package com.bluebird.mycontacts.models.post;

import java.io.Serializable;

public class UserLikeModel implements Serializable {

    private String username;

    private String pro_pic;

    private String bio;

    public UserLikeModel(String username, String pro_pic, String bio) {
        this.username = username;
        this.pro_pic = pro_pic;
        this.bio = bio;
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
