package com.example.test.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SiteUser {
    // Security에 User라는 객체를 사용하고 있기때문에 가급적 피하는게 좋다1
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String username;
    private String password;
    @Column(unique = true)
    private String email;


}
