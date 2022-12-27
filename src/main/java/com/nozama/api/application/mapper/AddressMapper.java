package com.nozama.api.application.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.nozama.api.application.dto.response.address.AddressResponse;
import com.nozama.api.domain.entity.Address;

@Component
public class AddressMapper {
  
  private final ModelMapper mapper = new ModelMapper();

  public AddressResponse toAddressResponse(Address address) {
    final var customerId = address.getCustomer().getId();
    final var addressResponse = mapper.map(address, AddressResponse.class);
    addressResponse.setCustomerId(customerId);
    addressResponse.setLinks();
    return addressResponse;
  }

  public List<AddressResponse> toAddressResponseList(List<Address> addresses) {
    return addresses.stream()
      .map((address) -> this.toAddressResponse(address))
      .toList();
  }

}
