package com.rishabh.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class OrderInputDTO {
	
	private String fullName;
	private String fullAddress;
	private String contactNumber;
	private String alternateContactNumber;
	private List<OrderProductQuantityDTO> orderProductQuantityDTOList;

}
