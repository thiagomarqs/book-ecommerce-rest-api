package com.nozama.api.domain.usecase.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.nozama.api.domain.entity.User;
import com.nozama.api.domain.exception.EntityNotFoundException;
import com.nozama.api.domain.repository.UserRepository;

@Component
public class ManageUser {
  
  @Autowired
  private UserRepository userRepository;
  
  public User loadUserByEmail(String email) throws UsernameNotFoundException {
    return userRepository
      .findByEmail(email)
      .orElseThrow(() -> new EntityNotFoundException(String.format("No user with the email '%s' could be found", email)));
  }
  
}
