package com.nozama.api.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nozama.api.domain.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
  
  Optional<Role> findByRole(String role);

}
