package com.example.mybatispractic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(){
        return "webcontent/WEB-INF/views/index.jsp";
    }
}
