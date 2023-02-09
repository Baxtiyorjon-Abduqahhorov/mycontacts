package com.bluebird.mycontacts.models.post;

import java.io.Serializable;
import java.util.List;

public class PostResultModel implements Serializable {

    private List<UserModel> contacts20;

    private List<PostModel> posts;

    public PostResultModel(List<UserModel> contacts20, List<PostModel> posts) {
        this.contacts20 = contacts20;
        this.posts = posts;
    }

    public List<UserModel> getContacts20() {
        return contacts20;
    }

    public void setContacts20(List<UserModel> contacts20) {
        this.contacts20 = contacts20;
    }

    public List<PostModel> getPosts() {
        return posts;
    }

    public void setPosts(List<PostModel> posts) {
        this.posts = posts;
    }
}
