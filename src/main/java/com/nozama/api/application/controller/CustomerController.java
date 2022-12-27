package com.nozama.api.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nozama.api.application.dto.request.address.AddressCreateRequest;
import com.nozama.api.application.dto.request.address.AddressUpdateRequest;
import com.nozama.api.application.dto.response.address.AddressResponse;
import com.nozama.api.application.mapper.AddressMapper;
import com.nozama.api.application.mapper.EntityMapper;
import com.nozama.api.domain.entity.Address;
import com.nozama.api.domain.usecase.customer.ManageCustomerAddresses;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/customers")
@Tag(
	name = "Author", 
	description = "Operations for managing customers."
)
public class CustomerController {

  @Autowired
  private ManageCustomerAddresses manageCustomerAddressesUseCase;

  @Autowired
  private EntityMapper entityMapper;

  @Autowired
  private AddressMapper addressMapper;

  @GetMapping(
    path = "/{id}/addresses", 
    produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
  )
  @Operation(
    summary = "All addresses",
    description = "Gets all the addresses of a customer.",
    tags = { "Customer", "Address" }
  )
  @PreAuthorize("#customerId == principal.id")
  public ResponseEntity<List<AddressResponse>> findAddresses(@PathVariable(value = "id") Long customerId) {
    final var customerAddresses = manageCustomerAddressesUseCase.getAddresses(customerId);
    final var response = addressMapper.toAddressResponseList(customerAddresses);
    
    return ResponseEntity.ok(response);
  }

  @GetMapping(
    path = "/{id}/addresses/{addressId}",
    produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
  )
  @Operation(
    summary = "Find address.",
    description = "Finds an address of a customer.",
    tags = { "Customer", "Address" }
  )
  @PreAuthorize("#customerId == principal.id")
  public ResponseEntity<AddressResponse> findAddress(@PathVariable(value = "id") Long customerId, @PathVariable(value = "addressId") Long addressId) {
    final var address = manageCustomerAddressesUseCase.findAddress(customerId, addressId);
    final var response = addressMapper.toAddressResponse(address).setLinks();

    return ResponseEntity.ok(response);
  }

  @PostMapping(
    path = "/{id}/addresses", 
    consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, 
    produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
  )
  @Operation(
    summary = "New address.",
    description = "Adds a new address to a customer. Returns all the addresses of the customer after the addition.",
    tags = { "Customer", "Address" }
  )
  @PreAuthorize("#customerId == principal.id")
  public ResponseEntity<List<AddressResponse>> addAddress(@PathVariable(value = "id") Long customerId, @RequestBody AddressCreateRequest addressRequest) {
    final var address = entityMapper.mapEntity(addressRequest, Address.class);
    final var customerAddresses = manageCustomerAddressesUseCase.addAddress(customerId, address);
    final var response = addressMapper.toAddressResponseList(customerAddresses);
    final var uri = Link.of("/api/customers/{id}/addresses").expand(customerId).toUri();

    return ResponseEntity.created(uri).body(response);
  }

  @PutMapping(
    path = "/{id}/addresses/{addressId}", 
    consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, 
    produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
  )
  @Operation(
    summary = "Update address.",
    description = "Updates an existing address of a customer. Returns all the addresses of the customer after the update.",
    tags = { "Customer", "Address" }
  )
  @PreAuthorize("#customerId == principal.id")
  public ResponseEntity<List<AddressResponse>> updateAddress(@PathVariable(value = "id") Long customerId, @PathVariable(value = "addressId") Long addressId, @RequestBody AddressUpdateRequest addressRequest) {
    final var address = entityMapper.mapEntity(addressRequest, Address.class);
    address.setId(addressId);    

    final var customerAddresses = manageCustomerAddressesUseCase.updateAddress(customerId, address);
    final var response = addressMapper.toAddressResponseList(customerAddresses);
    
    return ResponseEntity.ok(response);
  }

  @DeleteMapping(
    path = "/{id}/addresses/{addressId}", 
    produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
  )
  @Operation(
    summary = "Delete address.",
    description = "Deletes an address from a customer. If the address is the only one of its type (eg.: the customer's only billing address), it cannot be deleted.",
    tags = { "Customer", "Address" }
  )
  @PreAuthorize("#customerId == principal.id")
  public ResponseEntity<?> deleteAddress(@PathVariable(value = "id") Long customerId, @PathVariable(value = "addressId") Long addressId) {
    manageCustomerAddressesUseCase.deleteAddress(customerId, addressId);
    return ResponseEntity.ok().build();
  }


}
