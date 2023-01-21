package com.bluebird.mycontacts.models;

import java.io.Serializable;

public class RegisterResult implements Serializable {

    private boolean status;

    private String message;

    public RegisterResult(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
