package com.bluebird.mycontacts.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "")
public class Users implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
