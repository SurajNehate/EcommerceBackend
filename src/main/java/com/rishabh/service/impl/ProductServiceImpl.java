package com.rishabh.service.impl;
import com.rishabh.entity.Cart;
import com.rishabh.entity.Product;
import com.rishabh.entity.User;
import com.rishabh.filter.JWTAuthFilter;
import com.rishabh.repository.CartRepository;
import com.rishabh.repository.ProductRepository;
import com.rishabh.repository.UserRepository;
import com.rishabh.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository, CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }


    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product, Long productId) {
        Product existingProduct = productRepository.findById(productId).get();
        existingProduct.setProductName(product.getProductName());
        existingProduct.setProductDescription(product.getProductDescription());
        existingProduct.setProductPrice(product.getProductPrice());
        existingProduct.setProductQuantity(product.getProductQuantity());
        return productRepository.save(existingProduct);
    }


    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public List<Product> getProductDetails(boolean isSingeProductCheckout, Integer productId) {
        if(isSingeProductCheckout && productId != 0) {
            List<Product> list= new ArrayList<>();
            Product product = productRepository.findById(Long.valueOf(productId)).get();
            list.add(product);
            return list;
        }else {

            String username = JWTAuthFilter.CURRENT_USER;
            User user = userRepository.findById(Integer.valueOf(username)).get();
            List<Cart>  carts= cartRepository.findByUser(user);

            return carts.stream().map(x -> x.getProduct()).collect(Collectors.toList());

        }
    }
}
