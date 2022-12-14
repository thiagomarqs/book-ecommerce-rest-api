package com.nozama.api.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nozama.api.domain.entity.Customer;
import com.nozama.api.domain.repository.CustomerRepository;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/customers")
@Tag(name = "Customer", description = "Operations for managing customers.")
public class CustomerController {

  @Autowired
  private CustomerRepository repository;

  @PostMapping(
    consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, 
    produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
  )
  public ResponseEntity<Customer> create(@RequestBody Customer customer) {
    repository.save(customer);
    return ResponseEntity.ok(customer);
  }
  
}
