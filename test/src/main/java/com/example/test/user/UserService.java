package com.example.test.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String email, String password){

        // 비밀번호

        String encode = passwordEncoder.encode(password);

        SiteUser siteUser = new SiteUser(username, encode, email);
        userRepo.save(siteUser);
        return siteUser;
    }


}
