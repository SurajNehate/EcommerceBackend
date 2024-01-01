package com.rishabh.controller;
import com.rishabh.service.ProductService;
import com.rishabh.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productServiceImpl;
    @Autowired
    ProductController(ProductService productServiceImpl){
        this.productServiceImpl = productServiceImpl;
    }


    @GetMapping
    @PreAuthorize("hasAuthority('READ_PRODUCT')")
    public List<Product> getProducts(){
        return productServiceImpl.getProducts();
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('WRITE_PRODUCT')")
    public String addProduct(@RequestBody Product product){
        productServiceImpl.addProduct(product);
        return "Product added successfully";
    }

    @PutMapping("/updateproduct/{productId}")
    @PreAuthorize("hasAuthority('WRITE_PRODUCT')")
    public String updateProduct(@RequestBody Product product, @PathVariable Long productId){
        productServiceImpl.updateProduct(product, productId);
        return "Product updated successfully";
    }

    @DeleteMapping("/deleteproduct/{productId}")
    @PreAuthorize("hasAuthority('WRITE_PRODUCT')")
    public String deleteProduct(@PathVariable Long productId){
        productServiceImpl.deleteProduct(productId);
        return "Product deleted successfully";
    }


    //@PreAuthorize("hasRole('User')")
    @GetMapping({"/getProductDetails/{isSingeProductCheckout}/{productId}"})
    public List<Product> getProductDetails(@PathVariable(name="isSingeProductCheckout") boolean isSingeProductCheckout,
                                           @PathVariable(name= "productId") Integer productId) {

        return productServiceImpl.getProductDetails(isSingeProductCheckout, productId);


    }

}
