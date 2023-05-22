package com.psr.seatservice.domian.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNum;
    private String userId;
    private String password;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String role;

    public User(String userId, String password, String name, String address, String phone, String email, String role) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.role = role;
    }
}
