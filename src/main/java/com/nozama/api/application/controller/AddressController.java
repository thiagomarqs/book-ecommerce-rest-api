package com.nozama.api.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nozama.api.domain.entity.Address;
import com.nozama.api.domain.repository.AddressRepository;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/addresses")
@Tag(name = "Addresses", description = "Operations for managing addresses.")
public class AddressController {

  @Autowired
  private AddressRepository repository;

  @PostMapping(
    consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, 
    produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
  )
  public ResponseEntity<Address> create(@RequestBody Address address) {
    repository.save(address);
    return ResponseEntity.ok(address);
  }
  
}
