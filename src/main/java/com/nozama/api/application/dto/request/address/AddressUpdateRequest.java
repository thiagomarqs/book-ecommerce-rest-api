package com.nozama.api.application.dto.request.address;

import com.nozama.api.domain.enums.AddressType;

public class AddressUpdateRequest {
  
  private AddressType type;
  private String address;
  private String number;
  private String complement;
  private String cep;
  private String city;
  private String state;
  private String uf;
  private Boolean active;

  public AddressType getType() {
    return type;
  }
  public void setType(AddressType type) {
    this.type = type;
  }
  public String getAddress() {
    return address;
  }
  public void setAddress(String address) {
    this.address = address;
  }
  public String getNumber() {
    return number;
  }
  public void setNumber(String number) {
    this.number = number;
  }
  public String getComplement() {
    return complement;
  }
  public void setComplement(String complement) {
    this.complement = complement;
  }
  public String getCep() {
    return cep;
  }
  public void setCep(String cep) {
    this.cep = cep;
  }
  public String getCity() {
    return city;
  }
  public void setCity(String city) {
    this.city = city;
  }
  public String getState() {
    return state;
  }
  public void setState(String state) {
    this.state = state;
  }
  public String getUf() {
    return uf;
  }
  public void setUf(String uf) {
    this.uf = uf;
  }
  public Boolean getActive() {
    return active;
  }
  public void setActive(Boolean active) {
    this.active = active;
  }
  
 
}