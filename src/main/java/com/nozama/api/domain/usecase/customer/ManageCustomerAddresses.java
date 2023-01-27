package com.nozama.api.domain.usecase.customer;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import com.nozama.api.domain.entity.Address;
import com.nozama.api.domain.entity.Customer;
import com.nozama.api.domain.exception.EntityNotFoundException;
import com.nozama.api.domain.repository.AddressRepository;
import com.nozama.api.domain.repository.CustomerRepository;

@Component
public class ManageCustomerAddresses {

  private final CustomerRepository customerRepository;
  private final AddressRepository addressRepository;

  public ManageCustomerAddresses(CustomerRepository customerRepository, AddressRepository addressRepository) {
    this.customerRepository = customerRepository;
    this.addressRepository = addressRepository;
  }

  public Customer findCustomerById(Long id) {
    final var customerNotFoundMessage = getNotFoundMessage(id);
    return customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(customerNotFoundMessage));
  }

  public List<Address> addAddress(Long customerId, Address address) {
    final var customer = findCustomerById(customerId);
    
    address.setActive(true);
    address.setCreatedAt(LocalDate.now());
    
    customer.addAddress(address);
    customerRepository.save(customer);

    return customer.getAddresses();
  }

  public List<Address> getAddresses(Long customerId) {
    return this.customerRepository.findAddressesByCustomerId(customerId);
  }

  public List<Address> updateAddress(Long customerId, Address updatedAddress) {
    final var addressToUpdate = find(customerId, updatedAddress.getId());
    
    updatedAddress.setCustomer(addressToUpdate.getCustomer());
    updatedAddress.setCreatedAt(addressToUpdate.getCreatedAt());

    addressRepository.save(updatedAddress);

    return customerRepository.findAddressesByCustomerId(customerId);
  }

  public Address find(Long customerId, Long addressId) {
    final var customer = findCustomerById(customerId);
    
    return customer.getAddress(addressId);
  }

  public List<Address> findAllAddresses(Long customerId) {
    final var customer = findCustomerById(customerId);

    return customer.getAddresses();
  }

  public void deleteAddress(Long customerId, Long addressId) {
    final var customer = findCustomerById(customerId);

    customer.deleteAddress(addressId);

    customerRepository.save(customer);
  }

  private String getNotFoundMessage(Long customerId) {
    return String.format("Customer with ID %d not found", customerId);
  }

}
