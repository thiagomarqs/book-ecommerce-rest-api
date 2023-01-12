package com.nozama.api.domain.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import com.nozama.api.domain.enums.AddressType;
import com.nozama.api.domain.exception.EntityNotFoundException;
import com.nozama.api.domain.exception.OperationNotAllowedException;
import com.nozama.api.domain.validation.EighteenOrOlder;

@Entity
public class Customer implements Serializable {

  @Id
  private Long id;

  @MapsId
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @NotBlank(message = "The full name is required.")
  @Size(max = 255, message = "The full name must not exceed 255 characters.")
  private String fullName;

  @NotNull(message = "The birth date is required.")
  @Past
  @EighteenOrOlder(message = "You must be eighteen or older to register.")
  private LocalDate birthDate;

  @NotBlank(message = "The CPF is required.")
  @CPF(message = "The informed CPF is not valid. It must contain exactly 14 characters, including dots and one hyphen. Example: 111.222.333-00.")
  @Column(unique = true)
  private String cpf;

  @OneToMany(
    mappedBy = "customer", 
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private List<Address> addresses = new ArrayList<>();

  @OneToMany(
    mappedBy = "customer", 
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private List<CartItem> cartItems = new ArrayList<>();

  @Column(updatable = false)
  private LocalDate registeredAt;

  public Customer() {}
  
  public Customer(
      User user,
      String fullName,
      LocalDate birthDate,
      String cpf,
      LocalDate registeredAt) {
    this.user = user;
    this.fullName = fullName;
    this.birthDate = birthDate;
    this.cpf = cpf;
    this.registeredAt = registeredAt;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public LocalDate getRegisteredAt() {
    return registeredAt;
  }

  public void setRegisteredAt(LocalDate registeredAt) {
    this.registeredAt = registeredAt;
  }

  public void addAddress(Address address) {
    if(address == null) return;

    this.addresses.add(address);
    address.setCustomer(this);
  }

  public List<Address> getAddresses() {
    return addresses;
  }
  
  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public List<CartItem> getCartItems() {
    return cartItems;
  }

  public void addCartItems(List<CartItem> items) {
    items.forEach(this::addCartItem);
  }
  
  public void addCartItem(CartItem item) {
    item.setCustomer(this);
    cartItems.add(item);
  }

  public Optional<Address> getAddress(Long addressId) {
    return addresses.stream()
      .filter(a -> a.getId().equals(addressId))
      .findFirst();
  }

  public void deleteAddress(Long addressId) {
    if(!hasAddress(addressId)) throw new EntityNotFoundException("The provided address does not belong to this customer.");
    
    var address = getAddress(addressId).get();
    var addressType = address.getType();

    if(isAddressTheOnlyOneOfItsType(addressType)) { 
      var cantDeleteAddressErrorMessage = String.format("Cannot delete this %s address because it is the only of its type. Please, add another address of the same type to be able to delete this one.", addressType.name().toLowerCase());
      throw new OperationNotAllowedException(cantDeleteAddressErrorMessage);
    }

    Predicate<? super Address> matchesId = a -> a.getId().equals(addressId);
    
    addresses.removeIf(matchesId);
  }

  public Boolean hasAddress(Long addressId) {
    Predicate<? super Address> matchesId = a -> a.getId().equals(addressId);

    return addresses.stream().anyMatch(matchesId);
  }

  public Boolean isAddressTheOnlyOneOfItsType(AddressType type) {
    Predicate<? super Address> onlyAddressesOfThisType = a -> a.getType().equals(type);
    
    return addresses.stream()
      .filter(onlyAddressesOfThisType)
      .count() == 1;
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
    Customer other = (Customer) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }
  
}
