package com.rishabh.controller;
import com.rishabh.entity.Cart;
import com.rishabh.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private CartService cartServiceImpl;

	@PreAuthorize("hasAuthority('WRITE_READ_CART')")
	@GetMapping({"/addToCart/{productId}"})
	public Cart addTocart(@PathVariable(name="productId") Integer productId) {
		return cartServiceImpl.addToCart(productId);
		
	}
	
	@DeleteMapping({"/deleteCartItem/{cartId}"})
	public void deleteCartItem(@PathVariable(name= "cartId") Integer cartId) {
		cartServiceImpl.deleteCartItem(cartId);
	}



	@PreAuthorize("hasAuthority('WRITE_READ_CART')")
	@GetMapping({"/getCartDetails"})
	public List<Cart> getCartDetails() {
		return cartServiceImpl.getCartDetails();

	}
	

}
