package com.nozama.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nozama.api.domain.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{
  
}
