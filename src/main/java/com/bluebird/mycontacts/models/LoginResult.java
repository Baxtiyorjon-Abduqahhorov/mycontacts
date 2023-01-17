package com.bluebird.mycontacts.models;

import java.io.Serializable;

public class LoginResult implements Serializable {

    private boolean status;

    private String message;

    private String tokenType = null;

    private String token = null;

    public LoginResult(boolean status, String message, String tokenType, String token) {
        this.status = status;
        this.message = message;
        this.tokenType = tokenType;
        this.token = token;
    }

    public boolean isStatus() {
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

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
