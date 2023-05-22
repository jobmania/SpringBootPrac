package com.example.mybatispractic.domain;

import lombok.Data;

@Data
public class User {

    private String id;
    private String name;
    private String phone;
    private String address;

    public User(String id, String name, String phone, String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }
}
