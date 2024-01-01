package com.rishabh.service;
import com.rishabh.entity.Product;
import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
public interface ProductService {
    List<Product> getProducts();

    Product addProduct(Product product);

    Product updateProduct(Product product, Long productId);

    void deleteProduct(Long productId);

    List<Product> getProductDetails(boolean isSingeProductCheckout, Integer productId);
}
