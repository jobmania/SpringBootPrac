package com.example.test.domain;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class HelloLombok {

    private String hello;
    private int lombok;

    public static void main(String[] args) {
        HelloLombok helloLombok = new HelloLombok("엄준식",10);


        System.out.println(helloLombok.getHello());
        System.out.println(helloLombok.getLombok());
    }
}
