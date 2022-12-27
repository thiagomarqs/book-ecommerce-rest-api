package com.nozama.api.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nozama.api.domain.entity.Address;
import com.nozama.api.domain.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
  
  @Query("SELECT c.addresses FROM Customer c WHERE c.id = :id")
  List<Address> findAddressesByCustomerId(Long id);

}
