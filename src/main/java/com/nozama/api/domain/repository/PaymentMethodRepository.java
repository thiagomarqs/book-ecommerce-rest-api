package com.nozama.api.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nozama.api.domain.entity.PaymentMethod;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, UUID>{
  
  @Query("SELECT p FROM PaymentMethod p WHERE p.customer.id = :id")
  List<PaymentMethod> findAllByCustomerId(Long id);
  
}
