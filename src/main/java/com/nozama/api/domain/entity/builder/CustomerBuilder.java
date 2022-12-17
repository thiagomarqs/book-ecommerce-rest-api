package com.nozama.api.domain.entity.builder;

import java.time.LocalDate;

import com.nozama.api.domain.entity.Customer;
import com.nozama.api.domain.entity.User;

public class CustomerBuilder {

  private final Customer customer = new Customer();

  public static CustomerBuilder builder() {
    return new CustomerBuilder();
  }

  public CustomerBuilder withUser(User user) {
    this.customer.setUser(user);
    return this;
  }

  public CustomerBuilder withFullName(String fullName) {
    this.customer.setFullName(fullName);
    return this;
  }
  
  public CustomerBuilder withBirthDate(LocalDate birthDate) {
    this.customer.setBirthDate(birthDate);
    return this;
  }
  
  public CustomerBuilder withCpf(String cpf) {
    this.customer.setCpf(cpf);
    return this;
  }
  
  public Customer build() {
    return this.customer;
  }

}
