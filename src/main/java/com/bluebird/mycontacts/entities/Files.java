package com.bluebird.mycontacts.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "files")
public class Files implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
