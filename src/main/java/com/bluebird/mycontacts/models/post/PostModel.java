package com.bluebird.mycontacts.models.post;

import java.io.Serializable;
import java.util.List;

public class PostModel implements Serializable {

    private Long post_id;

    private String pro_pic;

    private String text;

    private UserLikeModel user;

    private List<String> last_four_post_pic;

    private String comments;

    private boolean post_liked;

    public PostModel(Long post_id, String pro_pic, String text, UserLikeModel user, List<String> last_four_post_pic, String comments, boolean postLiked) {
        this.post_id = post_id;
        this.pro_pic = pro_pic;
        this.text = text;
        this.user = user;
        this.last_four_post_pic = last_four_post_pic;
        this.comments = comments;
        this.post_liked = postLiked;
    }

    public Long getPost_id() {
        return post_id;
    }

    public void setPost_id(Long post_id) {
        this.post_id = post_id;
    }

    public String getPro_pic() {
        return pro_pic;
    }

    public void setPro_pic(String pro_pic) {
        this.pro_pic = pro_pic;
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
