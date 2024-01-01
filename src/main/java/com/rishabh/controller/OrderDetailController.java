package com.rishabh.controller;
import com.rishabh.dto.OrderDetailDTO;
import com.rishabh.dto.OrderInputDTO;
import com.rishabh.entity.OrderDetail;
import com.rishabh.service.OrderDetailService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderDetailController {
	
	@Autowired
	private OrderDetailService orderDetailService;
	
	
	
	//@PreAuthorize("hasAuthority('ROLE_USER')")
	@PostMapping({"/placeOrder/{isSingleProductCheckout}"})
	public void placeOrder(@PathVariable(name= "isSingleProductCheckout") boolean isSingleProductCheckout, @RequestBody OrderInputDTO orderInputDTO) throws StripeException {
		orderDetailService.placeOrder(orderInputDTO, isSingleProductCheckout);
		
	}
	
	//@PreAuthorize("hasAuthority('ROLE_USER')")
	@GetMapping({"/getOrderDetails"})
	public List<OrderDetailDTO> getOrderDetails() {
		return orderDetailService.getOrderDetails();
	}
	
	//@PreAuthorize("hasAuthority('ROLE_USER')")
	@GetMapping({"/getAllOrderDetails"})
	public List<OrderDetail> getAllOrderDetails() {
		return orderDetailService.getAllOrderDetails();
	}

}
