package com.nozama.api.domain.usecase.customer;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nozama.api.domain.entity.PaymentMethod;
import com.nozama.api.domain.repository.CustomerRepository;
import com.nozama.api.domain.repository.PaymentMethodRepository;

@Component
public class ManageCustomerPaymentMethods {
  
  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private PaymentMethodRepository paymentMethodRepository;

  @Autowired
  private ManageCustomer manageCustomerUseCase;

  public void add(Long customerId, PaymentMethod paymentMethod) {
    var customer = manageCustomerUseCase.find(customerId);
    customer.addPaymentMethod(paymentMethod); 
    customerRepository.save(customer);
  }

  public void delete(Long customerId, UUID paymentMethodId) {
    paymentMethodRepository.deleteById(paymentMethodId);
  }

  public List<PaymentMethod> getAll(Long customerId) {
    return paymentMethodRepository.findAllByCustomerId(customerId);
  }

  public PaymentMethod find(Long customerId, UUID paymentMethodId) {
    var customer = manageCustomerUseCase.find(customerId);
    return customer.getPaymentMethod(paymentMethodId);
  }

}
