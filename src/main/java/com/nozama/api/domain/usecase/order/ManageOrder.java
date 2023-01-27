package com.nozama.api.domain.usecase.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nozama.api.domain.entity.Order;
import com.nozama.api.domain.exception.EntityNotFoundException;
import com.nozama.api.domain.repository.CustomerRepository;
import com.nozama.api.domain.repository.OrderRepository;
import com.nozama.api.domain.usecase.customer.ManageCustomer;

@Component
public class ManageOrder {

  @Autowired
  private OrderRepository repository;

  @Autowired
  private ManageCustomer manageCustomerUseCase;

  public List<Order> findAll(Long customerId) {
    return repository.findByCustomerId(customerId);
  }

  public Order find(Long customerId, Long id) {
    final var customer = manageCustomerUseCase.find(customerId);
    final var order = customer.getOrder(id);
    return order;
  }
  
}
