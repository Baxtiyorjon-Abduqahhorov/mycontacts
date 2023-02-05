package com.bluebird.mycontacts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MycontactsApplication {

    public static void main(String[] args) {
        System.out.println(MycontactsApplication.class.getResource("").getPath());
        SpringApplication.run(MycontactsApplication.class, args);
    }

}
