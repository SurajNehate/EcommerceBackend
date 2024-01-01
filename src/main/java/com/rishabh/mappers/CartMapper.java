//package com.rishabh.mappers;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//@Mapper
//public interface CartMapper {
//    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);
//
//    @Mapping(target = "id", source = "cart.id")
//    @Mapping(target = "cartItems", source = "cartItems")
//    CartDto cartToCartDto(Cart cart, BigDecimal totalPrice, List<CartItemDto> cartItems);
//
//    @Mapping(target = "subTotal", expression = "java(cartItem.getSubTotal())")
//    CartItemDto cartItemToCartItemDto(CartItem cartItem);
//}