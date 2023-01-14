package com.bluebird.mycontacts.extra;

public class LoginResult {

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

    public LoginResult(boolean status, String message) {
        this.status = status;
        this.message = message;
    }
}
