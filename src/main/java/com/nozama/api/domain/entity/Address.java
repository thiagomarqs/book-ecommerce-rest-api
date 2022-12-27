package com.nozama.api.domain.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.nozama.api.domain.enums.AddressType;

@Entity
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @NotNull(message = "The address type is required. It can be either 'SHIPPING' or 'BILLING'.")
  private AddressType type;

  @NotBlank(message = "The address is required.")
  @Size(max = 255)
  private String address;

  @NotBlank(message = "The number is required.")
  @Size(max = 6)
  @Pattern(regexp = "^[0-9]*$")
  private String number;

  @Size(max = 255)
  private String complement;

  @NotBlank(message = "The CEP is required.")
  @Size(min = 9, max = 9, message = "The CEP must have exactly 9 characters, being composed only of numbers and only one hyphen.")
  @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "The informed CEP is not in a valid CEP format. Example of a correct CEP: 12345-000")
  private String cep;

  @NotBlank(message = "The city is required.")
  @Size(max = 100)
  private String city;

  @NotBlank(message = "The state is required.")
  @Size(max = 100)
  private String state;

  @NotBlank(message = "The UF is required.")
  @Size(min = 2, max = 2)
  private String uf;

  @ManyToOne
  @JoinColumn(name = "CUSTOMER_ID", updatable = false)
  private Customer customer;

  @FutureOrPresent
  @Column(updatable = false)
  private LocalDate createdAt;

  @NotNull
  private Boolean active;

  public Address() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Address other = (Address) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

}
