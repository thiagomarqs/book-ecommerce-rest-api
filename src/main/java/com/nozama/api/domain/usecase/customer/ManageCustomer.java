package com.nozama.api.domain.usecase.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nozama.api.domain.entity.Customer;
import com.nozama.api.domain.entity.User;
import com.nozama.api.domain.enums.RoleName;
import com.nozama.api.domain.exception.EntityNotFoundException;
import com.nozama.api.domain.repository.CustomerRepository;
import com.nozama.api.domain.repository.RoleRepository;

@Component
public class ManageCustomer {

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private RoleRepository roleRepository;

  public void register(Customer customer, User user) {
    var customerRole = roleRepository.findByRole("ROLE_".concat(RoleName.CUSTOMER.name())).get();
    var roles = List.of(customerRole);

    user.setRoles(roles);
    customerRepository.save(customer);
  }

  public Customer findById(Long id) {
    return customerRepository
      .findById(id)
      .orElseThrow(() -> new EntityNotFoundException(String.format("Customer with id %d not found.", id)));
  }

}
