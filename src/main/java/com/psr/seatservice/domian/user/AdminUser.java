package com.psr.seatservice.domian.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_admin")
public class AdminUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNum;
    private String userId;
    private String password;
    private String name;
    private String address;
    private String phone;

    public AdminUser(String userId, String password, String name, String address, String phone) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }
}
