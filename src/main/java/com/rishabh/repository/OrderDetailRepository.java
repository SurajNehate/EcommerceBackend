package com.rishabh.repository;
import com.rishabh.entity.OrderDetail;
import com.rishabh.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
	
	List<OrderDetail> findByUser(User user);

}
