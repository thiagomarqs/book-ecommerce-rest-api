package com.nozama.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nozama.api.domain.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
  
}
