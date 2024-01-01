package com.rishabh.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderProductQuantityDTO {
	
	private Integer productId;
	private Integer quantity;

}
