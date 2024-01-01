package com.rishabh.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class OrderDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderId;
	private String orderFullName;
	private String orderFullOrder;
	private String orderContactNumber;
	private String orderAlternateContactNumber;
	private String orderStatus;
	private Double orderAmount;
	
	@OneToOne(fetch = FetchType.LAZY)
	private Product product;
	
	@OneToOne
	private User user;

	public OrderDetail(String orderFullName, String orderFullOrder, String orderContactNumber,
			String orderAlternateContactNumber, String orderStatus, Double orderAmount, Product product, User user) {
		super();
		this.orderFullName = orderFullName;
		this.orderFullOrder = orderFullOrder;
		this.orderContactNumber = orderContactNumber;
		this.orderAlternateContactNumber = orderAlternateContactNumber;
		this.orderStatus = orderStatus;
		this.orderAmount = orderAmount;
		this.product = product;
		this.user = user;
	}

}
