package com.nozama.api.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nozama.api.domain.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
  
  Optional<User> findByEmail(String email);
}