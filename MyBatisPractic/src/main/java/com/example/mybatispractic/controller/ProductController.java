package com.example.mybatispractic.controller;

import com.example.mybatispractic.domain.Product;
import com.example.mybatispractic.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductMapper productMapper;

    @GetMapping("")
    public List<Product> getProducts(){
        List<Product> products = productMapper.selectAllProducts();
        return products;
    }

    @GetMapping("/{id}")
    public Product getProducts(@PathVariable("id") String id){
        Product product = productMapper.selectProductById(Long.valueOf(id));
        return product;
    }

    @PostMapping("")
    public void saveProduct(@RequestParam("name") String prodName ,@RequestParam("price") int prodPrice ){
        Product product = new Product();
        product.setProdName(prodName);
        product.setProdPrice(prodPrice);
        productMapper.insertProduct(product);

    }

    @PutMapping("/{id}")
    public void updateProduct(@PathVariable("id") Long id,@RequestParam("name") String prodName ,@RequestParam("price") int prodPrice ){
        Product product = new Product();
        product.setProdId(id);
        product.setProdName(prodName);
        product.setProdPrice(prodPrice);
        productMapper.updateProduct(product);

    }

    @DeleteMapping("/{id}")
    public void deleteMapping(@PathVariable("id") Long id){
        productMapper.deleteProductById(id);
    }


}
