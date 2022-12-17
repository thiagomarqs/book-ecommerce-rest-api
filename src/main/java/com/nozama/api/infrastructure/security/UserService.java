package com.nozama.api.infrastructure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nozama.api.domain.usecase.customer.ManageCustomer;

@Service
public class UserService implements UserDetailsService {

  @Autowired
  private ManageCustomer manageUserUseCase;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return manageUserUseCase.loadCustomerUserByEmail(username);
  }
  
}
