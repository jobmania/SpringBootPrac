package com.example.test.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class SiteUser {
    // Security에 User라는 객체를 사용하고 있기때문에 가급적 피하는게 좋다1
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;

    @Column(unique = true)
    private String username;
    private String password;
    @Column(unique = true)
    private String email;

    @Column(columnDefinition = "VARCHAR")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    public SiteUser(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userRole = UserRole.USER;
    }
    public SiteUser(String username, String password, String email,UserRole userRole) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userRole = userRole;
    }
}
