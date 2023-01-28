package com.bluebird.mycontacts.models;

import java.io.Serializable;

public class AvailableContactResult implements Serializable {

    private String contact_number;

    private String first_name;

    private String last_name;

    private String picture;

    private String bio;

    public AvailableContactResult(String contact_number, String first_name, String last_name, String picture, String bio) {
        this.contact_number = contact_number;
        this.first_name = first_name;
        this.last_name = last_name;
        this.picture = picture;
        this.bio = bio;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
