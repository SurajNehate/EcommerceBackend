//package com.rishabh.dto;
//
//import com.stripe.param.ChargeCreateParams;
//import lombok.Data;
//
//import java.math.BigDecimal;
//
//@Data
//public class PaymentDTO {
//    private String tokenId;
//    private BigDecimal amount;
//    private String currency;
//    private Object metadata;
//
//    public PaymentDTO(String tokenId, BigDecimal amount, String currency, Object metadata) {
//        this.tokenId = tokenId;
//        this.amount = amount;
//        this.currency = currency;
//        this.metadata = metadata;
//    }
//
//    public ChargeCreateParams toChargeParams() {
//        return new ChargeCreateParams.Builder()
//                .setAmount(amount.multiply(new BigDecimal("100")).longValue())
//                .setCurrency(currency)
//                .setDescription("Payment from Stripe Checkout")
//                .setSource(tokenId)
//                .build();
//    }
//}
