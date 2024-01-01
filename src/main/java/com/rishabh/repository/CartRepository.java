package com.rishabh.repository;
import com.rishabh.entity.Cart;
import com.rishabh.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
	
	List<Cart> findByUser(User user);

}
