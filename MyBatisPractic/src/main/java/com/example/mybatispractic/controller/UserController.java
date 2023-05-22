package com.example.mybatispractic.controller;

import com.example.mybatispractic.domain.User;
import com.example.mybatispractic.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {


    //userMapper 선언하고 생성자 주입
    private final UserMapper userMapper;

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable("id") String id) {
        System.out.println(id);
        User user = userMapper.getUser(id);
        return user;
    }

    @GetMapping("/users")
    public List<User> getUserList(){
        List<User> userList = userMapper.getUserList();
        return userList;
    }

    @PostMapping("/user")
    public void createUser( @RequestParam("id") String id,
                            @RequestParam("name") String name,
                            @RequestParam("phone") String phone,
                            @RequestParam("address") String address  ) {

        userMapper.insertUser(id, name, phone, address);
    }


    @PutMapping("/user/{id}")
    public void editUser( @PathVariable("id") String id,
                          @RequestParam("name") String name,
                          @RequestParam("phone") String phone,
                          @RequestParam("address") String address  ) {
        userMapper.updateUser(id, name, phone, address);
    }


}
