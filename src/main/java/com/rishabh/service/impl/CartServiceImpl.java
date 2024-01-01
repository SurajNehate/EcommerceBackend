package com.rishabh.service.impl;
import com.rishabh.entity.Cart;
import com.rishabh.entity.Product;
import com.rishabh.entity.User;
import com.rishabh.filter.JWTAuthFilter;
import com.rishabh.repository.CartRepository;
import com.rishabh.repository.ProductRepository;
import com.rishabh.repository.UserRepository;
import com.rishabh.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

	private final CartRepository cartRepo;
	private final ProductRepository productRepo;
	private final UserRepository userRepo;
	private final JWTAuthFilter jwtAuthFilter;

	@Autowired
    public CartServiceImpl(CartRepository cartRepo, ProductRepository productRepo, UserRepository userRepo, JWTService jwtService, JWTAuthFilter jwtAuthFilter) {
        this.cartRepo = cartRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Override
	public void deleteCartItem(Integer cartId) {
		cartRepo.deleteById(cartId);
	}
	
	@Override
	public Cart addToCart(Integer productId) {
		
		Product product = productRepo.findById(Long.valueOf(productId)).get();
		
		String username = jwtAuthFilter.CURRENT_USER;
		
		User user = null;
		
		if(username != null) {
			user = userRepo.findByUserName(username);
			
		}
		
		List<Cart> cartList = cartRepo.findByUser(user);
		List<Cart> filteredList = cartList.stream().filter(x -> x.getProduct().getProductId() == productId).collect(Collectors.toList());
		
		if(filteredList.size() > 0) {
			return null;
		}
		
		
		if(product != null && user != null) {
			Cart cart = new Cart(product, user);
			return cartRepo.save(cart);
		}
		return null;
	}
	
	@Override
	public List<Cart> getCartDetails(){
		String username = jwtAuthFilter.CURRENT_USER;
		User user = userRepo.findById(Integer.valueOf(username)).get();
		return cartRepo.findByUser(user);
		
	}
	
	

}
