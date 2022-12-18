package com.nozama.api.domain.vo;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import com.nozama.api.domain.entity.Address;
import com.nozama.api.domain.entity.Customer;

@Embeddable
public class CustomerAddressId {

  private Customer customer;
  private Address address;

  @ManyToOne(cascade = CascadeType.ALL)
  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  @ManyToOne(cascade = CascadeType.ALL)
  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((customer == null) ? 0 : customer.hashCode());
    result = prime * result + ((address == null) ? 0 : address.hashCode());
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
    CustomerAddressId other = (CustomerAddressId) obj;
    if (customer == null) {
      if (other.customer != null)
        return false;
    } else if (!customer.equals(other.customer))
      return false;
    if (address == null) {
      if (other.address != null)
        return false;
    } else if (!address.equals(other.address))
      return false;
    return true;
  }

}
