package com.nozama.api.application.mapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nozama.api.application.dto.request.cartItem.CartItemRequest;
import com.nozama.api.application.dto.response.cartItem.CartItemResponse;
import com.nozama.api.domain.entity.Book;
import com.nozama.api.domain.entity.CartItem;

@Component
public class CartItemMapper {

  @Autowired
  private EntityMapper mapper;

  @Autowired
  private BookMapper bookMapper;
  
  public CartItem toCartItem(CartItemRequest request, Book book) {
    var mapped = new CartItem();
    var bookPrice = book.getPrice();

    mapped.setBook(book);
    mapped.setPrice(bookPrice.getPrice());
    mapped.setUnits(request.getUnits());
    mapped.setCurrency(bookPrice.getCurrency());
    mapped.calculateAndSetSubtotal();

    return mapped;
  }

  public CartItemResponse toCartItemResponse(CartItem item) {
    var mapped = mapper.mapEntity(item, CartItemResponse.class);
    return mapped;
  }

  public List<CartItemResponse> toCartItemResponseList(List<CartItem> items) {
    return items.stream().map(this::toCartItemResponse).toList();
  }

}
