package com.nozama.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nozama.api.domain.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{
  
}
