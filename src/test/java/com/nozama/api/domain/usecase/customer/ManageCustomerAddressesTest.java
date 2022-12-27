package com.nozama.api.domain.usecase.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nozama.api.domain.entity.Address;
import com.nozama.api.domain.entity.Customer;
import com.nozama.api.domain.enums.AddressType;
import com.nozama.api.domain.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
public class ManageCustomerAddressesTest {

  @Mock
  CustomerRepository customerRepository;

  @InjectMocks
  ManageCustomerAddresses manageCustomerAddressesUseCase;

  @Test
  void When_AddingNullAddress_Expect_Exception() {
    assertThrows(RuntimeException.class, () -> manageCustomerAddressesUseCase.addAddress(1L, null));
  }

  @Test
  void When_UpdatingNullAddress_Expect_Exception() {
    assertThrows(RuntimeException.class, () -> manageCustomerAddressesUseCase.updateAddress(1L, null));
  }

  @Test
  void When_DeletingNullAddress_Expect_Exception() {
    assertThrows(RuntimeException.class, () -> manageCustomerAddressesUseCase.deleteAddress(1L, null));
  }

  @Test
  void Given_NoAddresses_When_Adding1Address_Expect_1Address() {
    var customer = new Customer();
    
    assertEquals(0, customer.getAddresses().size());

    when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

    var result = manageCustomerAddressesUseCase.addAddress(1L, mock(Address.class));

    assertEquals(1, result.size());
    assertEquals(1, customer.getAddresses().size());
  }

  @Test
  void When_AddingAddress_Expect_SetActiveAndCreatedAt() {
    var customer = new Customer();
    var address = new Address();

    assertNull(address.getActive());
    assertNull(address.getCreatedAt());

    when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
    manageCustomerAddressesUseCase.addAddress(1L, address);

    assertTrue(() -> address.getActive());
    assertNotNull(address.getCreatedAt());
  }

  @Test
  void Given_CustomerHasOnly1Address_When_DeletingAddress_Expect_Exception() {
    var customer = new Customer();
    customer.addAddress(new Address());

    assertEquals(1, customer.getAddresses().size());
    assertThrows(RuntimeException.class, () -> manageCustomerAddressesUseCase.deleteAddress(anyLong(), anyLong()));
  }

  @Test
  void Given_CustomerHasOnly1BillingAddress_When_DeletingBillingAddress_Expect_Exception() {
    var customer = new Customer();
    var address1 = new Address();
    var address2 = new Address();

    address1.setType(AddressType.BILLING);
    address2.setType(AddressType.SHIPPING);

    customer.addAddress(address1);
    customer.addAddress(address2);

    assertEquals(2, customer.getAddresses().size());
    assertThrows(RuntimeException.class, () -> manageCustomerAddressesUseCase.deleteAddress(anyLong(), anyLong()));
  }

  @Test
  void Given_CustomerHas2BillingAddress_When_DeletingBillingAddress_Expect_DeleteAddress() {
    var customer = new Customer();
    var address1 = new Address();
    var address2 = new Address();

    address1.setId(1L);
    address1.setType(AddressType.BILLING);
    address2.setId(2L);
    address2.setType(AddressType.BILLING);

    customer.setId(1L);
    customer.addAddress(address1);
    customer.addAddress(address2);

    assertEquals(2, customer.getAddresses().size());

    when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

    manageCustomerAddressesUseCase.deleteAddress(1L, 1L);

    assertEquals(1, customer.getAddresses().size());
  }

  @Test
  void Given_NoAddresses_When_DeletingAddresses_Expect_Exception() {
    var customer = new Customer();

    when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

    assertThrows(RuntimeException.class, () -> manageCustomerAddressesUseCase.deleteAddress(1L, 1L));
  }

}
