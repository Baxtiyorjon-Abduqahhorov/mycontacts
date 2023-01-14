package com.bluebird.mycontacts.entities;

import javax.persistence.*;

@Entity
@Table(name = "users_contacts")
public class UsersContacts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private UserInfo userInfo;

    @Column(name = "contact_number")
    private String contactNumber;
}
