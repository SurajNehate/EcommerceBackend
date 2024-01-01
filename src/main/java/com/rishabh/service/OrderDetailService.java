package com.rishabh.service;

import com.rishabh.dto.OrderDetailDTO;
import com.rishabh.dto.OrderInputDTO;
import com.rishabh.entity.OrderDetail;
import com.stripe.exception.StripeException;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetail> getAllOrderDetails();

    List<OrderDetailDTO> getOrderDetails();

    void placeOrder(OrderInputDTO orderInputDTO, boolean isSingleProductCheckout) throws StripeException;
}
