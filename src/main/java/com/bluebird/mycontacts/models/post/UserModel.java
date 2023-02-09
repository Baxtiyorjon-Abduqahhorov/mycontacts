package com.bluebird.mycontacts.models.post;

import java.io.Serializable;

public class UserModel implements Serializable {

    private Long id;

    private String pro_pic;

    private String username;

    public UserModel(Long id, String pro_pic, String username) {
        this.id = id;
        this.pro_pic = pro_pic;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPro_pic() {
        return pro_pic;
    }

    public void setPro_pic(String pro_pic) {
        this.pro_pic = pro_pic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
