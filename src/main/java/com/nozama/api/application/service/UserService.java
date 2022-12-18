package com.nozama.api.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nozama.api.domain.usecase.user.ManageUser;

@Service
public class UserService implements UserDetailsService {

  @Autowired
  private ManageUser manageUserUseCase;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return manageUserUseCase.loadUserByEmail(username);
  }
  
}
