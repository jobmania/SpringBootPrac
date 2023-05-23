package com.example.mybatispractic.mapper;

import com.example.mybatispractic.domain.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    ///  xml로 들고오기..
    Product selectProductById(Long id);
    List<Product> selectAllProducts();
    void insertProduct(Product product);
    void updateProduct(Product product);
    void deleteProductById(Long id);


}
