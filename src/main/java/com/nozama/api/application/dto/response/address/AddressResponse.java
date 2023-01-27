package com.nozama.api.application.dto.response.address;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.Link;

import com.nozama.api.application.controller.CustomerController;
import com.nozama.api.domain.enums.AddressType;

public class AddressResponse extends RepresentationModel<AddressResponse> {
  private Long id;
  private Long customerId;
  private AddressType type;
  private String address;
  private String number;
  private String complement;
  private String cep;
  private String city;
  private String state;
  private String uf;
  private LocalDate createdAt;
  private Boolean active;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

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

  public LocalDate getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDate createdAt) {
    this.createdAt = createdAt;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public AddressResponse setLinks() {

    Link self = linkTo(methodOn(CustomerController.class).findAddress(this.customerId, this.id))
      .withSelfRel()
      .expand(this.customerId, this.id);
    
    Link all = linkTo(methodOn(CustomerController.class).findAddresses(this.customerId))
      .withRel("addresses");

    Link[] links = {
      self,
      all
    };

    this.add(links);

    return this;
  }

}