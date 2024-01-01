package com.rishabh.service.impl;
import com.rishabh.dto.OrderDetailDTO;
import com.rishabh.dto.OrderInputDTO;
import com.rishabh.dto.OrderProductQuantityDTO;
import com.rishabh.entity.Cart;
import com.rishabh.entity.OrderDetail;
import com.rishabh.entity.Product;
import com.rishabh.entity.User;
import com.rishabh.filter.JWTAuthFilter;
import com.rishabh.repository.CartRepository;
import com.rishabh.repository.OrderDetailRepository;
import com.rishabh.repository.ProductRepository;
import com.rishabh.repository.UserRepository;
import com.rishabh.service.OrderDetailService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
	
	private static final String ORDER_PLACED = "Placed";  

	private final OrderDetailRepository orderDetailRepo;
	private final ProductRepository productRepo;
	private final UserRepository userRepo;
	private final CartRepository cartRepo;
	private final JWTAuthFilter jwtAuthFilter;
	//private final PaymentService paymentServiceImpl;
	@Autowired
    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepo, ProductRepository productRepo, UserRepository userRepo, CartRepository cartRepo, JWTAuthFilter jwtAuthFilter) {
        this.orderDetailRepo = orderDetailRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
        this.cartRepo = cartRepo;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Override
	public List<OrderDetail> getAllOrderDetails(){
		List<OrderDetail> orderDetails = new ArrayList<>();
		orderDetailRepo.findAll().forEach(e -> orderDetails.add(e));
		
		return orderDetails;
	}
	
	@Override
	public List<OrderDetailDTO> getOrderDetails() {
		String currentUser = jwtAuthFilter.CURRENT_USER;
		User user = userRepo.findByUserName(currentUser);
		List<OrderDetail> orderDetails = orderDetailRepo.findByUser(user);
		List<OrderDetailDTO> orderDetailDTOs = new ArrayList<>();
		for(OrderDetail o: orderDetails) {
			OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
			orderDetailDTO.setOrderFullName(o.getOrderFullName());
			orderDetailDTO.setOrderFullOrder(o.getOrderFullOrder());
			orderDetailDTO.setOrderContactNumber(o.getOrderContactNumber());
			orderDetailDTO.setOrderAlternateContactNumber(o.getOrderAlternateContactNumber());
			orderDetailDTO.setOrderStatus(o.getOrderStatus());
			orderDetailDTO.setOrderAmount(o.getOrderAmount());
			orderDetailDTO.setProductName(o.getProduct().getProductName());
			orderDetailDTO.setProductDiscountedPrice(o.getProduct().getProductDiscountedPrice());
			orderDetailDTO.setProductQuantity(o.getProduct().getProductQuantity());
//			OrderDetailDTO dto = orderDetailDTO.convertEntityToDto(o);
			orderDetailDTOs.add(orderDetailDTO);
		}
		
		return orderDetailDTOs;
	}
	
	@Override
	public void placeOrder(OrderInputDTO orderInputDTO, boolean isSingleProductCheckout) throws StripeException {
		List<OrderProductQuantityDTO> productQuantityList = orderInputDTO.getOrderProductQuantityDTOList();
		
		for(OrderProductQuantityDTO o: productQuantityList) {
			Product product = productRepo.findById(Long.valueOf(o.getProductId())).get();
			
			String currentUser = jwtAuthFilter.CURRENT_USER;
			User user= userRepo.findByUserName(currentUser);
			
			OrderDetail orderDetail = new OrderDetail(
					orderInputDTO.getFullName(),
					orderInputDTO.getFullAddress(),
					orderInputDTO.getContactNumber(),
					orderInputDTO.getAlternateContactNumber(),
					ORDER_PLACED,
					product.getProductDiscountedPrice()*o.getQuantity(),
					product,
					user);
			
			if(!isSingleProductCheckout) {
				List<Cart> carts= cartRepo.findByUser(user);
				carts.stream().forEach(x -> cartRepo.deleteById(x.getCartId()));
				
			}
//			BigDecimal totalPrice = BigDecimal.valueOf(orderDetail.getOrderAmount());
//			PaymentIntent paymentIntent = paymentServiceImpl.createPaymentIntent(totalPrice);
//			PaymentDTO paymentDto = new PaymentDTO(paymentIntent.getClientSecret(), totalPrice, "usd", orderDetail.getOrderId());

			orderDetailRepo.save(orderDetail);
		}
	}
	
	

}
