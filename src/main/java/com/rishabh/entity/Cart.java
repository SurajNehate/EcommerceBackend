package com.rishabh.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table
@NoArgsConstructor
@Data
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cartId;
	@OneToOne
	private Product product;
	@OneToOne
	private User user;
	
	public Cart(Product product, User user) {
		super();
		this.product = product;
		this.user = user;
	}

}
