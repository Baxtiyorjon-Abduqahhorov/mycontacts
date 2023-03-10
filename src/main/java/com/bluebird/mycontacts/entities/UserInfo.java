package com.bluebird.mycontacts.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user_info")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String first_name;

    @Column(name = "last_name")
    private String last_name;

    @Column(name = "picture", unique = true)
    private String pro_pic;

    @Column(name = "bio")
    private String bio;

    @Column(name = "phone", unique = true, nullable = false)
    private String phone;

    @OneToMany(cascade = CascadeType.ALL)
    private List<UserInfo> userInfos;

    @ManyToMany
    @JoinTable(name = "post_likes",
            joinColumns = @JoinColumn(name = "user_info_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"))
    private Set<Posts> posts = new LinkedHashSet<>();

    public Set<Posts> getPosts() {
        return posts;
    }

    public void setPosts(Set<Posts> posts) {
        this.posts = posts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPro_pic() {
        return pro_pic;
    }

    public void setPro_pic(String pro_pic) {
        this.pro_pic = pro_pic;
    }
}
