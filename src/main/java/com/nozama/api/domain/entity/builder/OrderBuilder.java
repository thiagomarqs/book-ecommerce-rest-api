package com.nozama.api.domain.entity.builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.nozama.api.domain.entity.CartItem;
import com.nozama.api.domain.entity.Customer;
import com.nozama.api.domain.entity.Order;
import com.nozama.api.domain.entity.OrderItem;
import com.nozama.api.domain.enums.OrderStatus;
import com.nozama.api.domain.util.DomainUtils;

public class OrderBuilder {

  private Order order = new Order();

  public static OrderBuilder builder() {
    return new OrderBuilder();
  }

  public OrderBuilder withCustomer(Customer customer) {
    order.setCustomer(customer);
    return this;
  }

  public OrderBuilder withCartItems(List<CartItem> cartItems) {
    var orderItems = cartItems
      .stream()
      .map(c -> OrderItem.from(c, order))
      .collect(Collectors.toSet());

    order.setItems(orderItems);

    return this;
  }

  public Order build() {
    order.calculateAndSetTotal();
    order.setStatus(OrderStatus.ONGOING);
    order.setCreatedAt(LocalDateTime.now());
    order.setCode(DomainUtils.generateOrderCode());
    return order;
  }

  
}
