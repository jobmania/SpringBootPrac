package com.example.test.user;

import com.example.test.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public SiteUser getUser(String username){
        Optional<SiteUser> siteUser = userRepo.findByUsername(username);
        if(siteUser.isPresent()){
            return siteUser.get();
        }else {
            throw new DataNotFoundException("siteuser not found");
        }

    }


}
