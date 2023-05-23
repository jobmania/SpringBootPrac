package com.example.mybatispractic.domain;

import lombok.Data;

@Data
public class Product {
    private Long prodId;	 //CamelCase 소문자단어 다음에 대문자로시작함
    private String prodName; //DB에서는 prod_id , prod_name, prod_price
    private int prodPrice;


}
