package com.example.mybatispractic.mapper;

import com.example.mybatispractic.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from user where id=#{?}")
    User getUser(String id);


    @Select("select * from user")
    List<User> getUserList();

    @Insert("insert into user values (#{id},#{name},#{phone},#{address})")
    void insertUser(String id, String name, String phone, String address);


    @Update("Update user SET name=#{name}, phone=#{phone}, address=#{address} WHERE id=#{id}")
    int updateUser(String id, String name, String phone, String address);
}
