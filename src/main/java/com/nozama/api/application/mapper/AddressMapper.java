package com.nozama.api.application.mapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nozama.api.application.dto.response.address.AddressResponse;
import com.nozama.api.domain.entity.Address;

@Component
public class AddressMapper {
  
  @Autowired
  private EntityMapper mapper;

  public List<AddressResponse> toAddressResponseList(List<Address> addresses) {
    return addresses.stream()
      .map((address) -> mapper.mapEntity(address, AddressResponse.class).setLinks())
      .toList();
  }

}
