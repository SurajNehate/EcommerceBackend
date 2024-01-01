package com.rishabh.service;

import com.rishabh.entity.Cart;

import java.util.List;

public interface CartService {
    void deleteCartItem(Integer cartId);

    Cart addToCart(Integer productId);

    List<Cart> getCartDetails();
}
