package com.example.test;

import org.springframework.web.bind.annotation.RequestMapping;

public class MainController {

    @RequestMapping("/")
    public String root() {
        return "forward:/question/list";
    }


}
