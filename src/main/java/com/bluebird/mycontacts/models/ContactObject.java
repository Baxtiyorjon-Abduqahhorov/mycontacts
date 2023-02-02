package com.bluebird.mycontacts.models;

import com.bluebird.mycontacts.entities.UsersContacts;

public class ContactObject {

    private String number;

    private String name;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        final UsersContacts user = (UsersContacts) obj;
        return this.getName().equals(user.getContactName()) && this.getNumber().equals(user.getContactNumber());
    }
}
