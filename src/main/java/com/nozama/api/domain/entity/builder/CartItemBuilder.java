package com.nozama.api.domain.entity.builder;

import com.nozama.api.domain.entity.Book;
import com.nozama.api.domain.entity.CartItem;
import com.nozama.api.domain.entity.Customer;

public class CartItemBuilder {
 
  public static CartItem from(Customer customer, Book book, Integer units) {
    return new CartItem(customer, book, book.getPrice(), units);
  }

}
