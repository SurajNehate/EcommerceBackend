package com.rishabh.dto;

import com.rishabh.entity.OrderDetail;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class OrderDetailDTO {

    private String orderFullName;
    private String orderFullOrder;
    private String orderContactNumber;
    private String orderAlternateContactNumber;
    private String orderStatus;
    private Double orderAmount;
    private String productName;
    private Double productDiscountedPrice;
    private Integer productQuantity;

//    @Autowired
//    private ModelMapper modelMapper;
//    public OrderDetail convertDtoToEntity(OrderDetailDTO orderDetailDTO) {
//        modelMapper.getConfiguration()
//                .setMatchingStrategy(MatchingStrategies.LOOSE);
//        OrderDetail orderDetail = modelMapper.map(orderDetailDTO, OrderDetail.class);
//        return orderDetail;
//    }
//
//    public OrderDetailDTO convertEntityToDto(OrderDetail orderDetail) {
//        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
//
//        // Explicitly map additional properties
//        modelMapper.typeMap(OrderDetail.class, OrderDetailDTO.class)
//                .addMapping(src -> src.getProduct().getProductName(), OrderDetailDTO::setProductName)
//                .addMapping(src -> src.getProduct().getProductDiscountedPrice(), OrderDetailDTO::setProductDiscountedPrice)
//                .addMapping(src -> src.getProduct().getProductQuantity(), OrderDetailDTO::setProductQuantity);
//
//        OrderDetailDTO orderDetailDTO = modelMapper.map(orderDetail, OrderDetailDTO.class);
//        return orderDetailDTO;
//    }

}
