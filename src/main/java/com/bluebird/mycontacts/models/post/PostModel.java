package com.bluebird.mycontacts.models.post;

import java.io.Serializable;
import java.util.List;

public class PostModel implements Serializable {

    private Long post_id;

    private String picture;

    private String text;

    private UserLikeModel user;

    private List<String> last_four_post_pic;

    private String comments;

    private boolean post_liked;

    public PostModel(Long post_id, String picture, String text, UserLikeModel user, List<String> last_four_post_pic, String comments, boolean post_liked) {
        this.post_id = post_id;
        this.picture = picture;
        this.text = text;
        this.user = user;
        this.last_four_post_pic = last_four_post_pic;
        this.comments = comments;
        this.post_liked = post_liked;
    }

    public Long getPost_id() {
        return post_id;
    }

    public void setPost_id(Long post_id) {
        this.post_id = post_id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserLikeModel getUser() {
        return user;
    }

    public void setUser(UserLikeModel user) {
        this.user = user;
    }

    public List<String> getLast_four_post_pic() {
        return last_four_post_pic;
    }

    public void setLast_four_post_pic(List<String> last_four_post_pic) {
        this.last_four_post_pic = last_four_post_pic;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public boolean isPost_liked() {
        return post_liked;
    }

    public void setPost_liked(boolean post_liked) {
        this.post_liked = post_liked;
    }
}
