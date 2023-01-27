package com.nozama.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nozama.api.domain.entity.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Long>{
  
}
