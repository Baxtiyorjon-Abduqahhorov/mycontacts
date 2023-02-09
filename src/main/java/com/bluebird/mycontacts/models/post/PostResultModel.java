package com.bluebird.mycontacts.models.post;

import java.io.Serializable;

public class PostResultModel implements Serializable {

    private Long user_id;

    private PostDataModel data;

    public PostResultModel(Long user_id, PostDataModel data) {
        this.user_id = user_id;
        this.data = data;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public PostDataModel getData() {
        return data;
    }

    public void setData(PostDataModel data) {
        this.data = data;
    }
}
