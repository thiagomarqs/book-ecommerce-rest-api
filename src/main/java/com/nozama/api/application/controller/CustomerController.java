package com.nozama.api.application.controller;

import java.util.List;

import javax.validation.Valid;

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
import com.nozama.api.application.dto.request.cartItem.CartItemRequest;
import com.nozama.api.application.dto.request.cartItem.UpdateCartItemUnitsRequest;
import com.nozama.api.application.dto.response.address.AddressResponse;
import com.nozama.api.application.dto.response.cartItem.CartItemResponse;
import com.nozama.api.application.mapper.AddressMapper;
import com.nozama.api.application.mapper.CartItemMapper;
import com.nozama.api.application.mapper.EntityMapper;
import com.nozama.api.domain.entity.Address;
import com.nozama.api.domain.usecase.book.ManageBook;
import com.nozama.api.domain.usecase.cart.ManageCart;
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
  private ManageCart manageCartUseCase;

  @Autowired
  private ManageBook manageBookUseCase;

  @Autowired
  private EntityMapper entityMapper;

  @Autowired
  private AddressMapper addressMapper;

  @Autowired
  private CartItemMapper cartItemMapper;

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

  @GetMapping(
    path = "/{id}/cart",
    produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
  )
  @Operation(
    summary = "Get Cart.",
    description = "Gets all items in the customer's cart.",
    tags = { "Customer", "Cart" }
  )
  @PreAuthorize("#customerId == principal.id")
  public ResponseEntity<List<CartItemResponse>> getCart(@PathVariable(value = "id") Long customerId) {
    var items = manageCartUseCase.getCart(customerId);
    var response = cartItemMapper.toCartItemResponseList(items);
    response = response.stream().map(i -> i.setLinks()).toList();
    return ResponseEntity.ok(response);
  }

  @GetMapping(
    path = "/{id}/cart/items/{itemId}",
    produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
  )
  @Operation(
    summary = "Get One Item.",
    description = "Gets a cart item by its id.",
    tags = { "Customer", "Cart" }
  )
  @PreAuthorize("#customerId == principal.id")
  public ResponseEntity<CartItemResponse> getCartItem(@PathVariable(value = "id") Long customerId, @PathVariable(value = "itemId") Long cartItemId) {
    var item = manageCartUseCase.getItem(customerId, cartItemId);
    var response = cartItemMapper.toCartItemResponse(item).setLinks();
    return ResponseEntity.ok(response);
  }

  @PostMapping(
    path = "/{id}/cart/items",
    produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
    consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
  )
  @Operation(
    summary = "Add item to cart.",
    description = "Adds an item to the customers' cart and returns all items if the operation is successful. If the book contained in the item was already added, the operation will fail.",
    tags = { "Customer", "Cart" }
  )
  @PreAuthorize("#customerId == principal.id")
  public ResponseEntity<List<CartItemResponse>> addItemToCart(@PathVariable(value = "id") Long customerId, @RequestBody CartItemRequest request) {
    var book = manageBookUseCase.findById(request.getBookId());
    var item = cartItemMapper.toCartItem(request, book);
    var items = manageCartUseCase.addItem(customerId, item);
    var response = cartItemMapper.toCartItemResponseList(items);
    response = response.stream().map(i -> i.setLinks()).toList();
    return ResponseEntity.ok(response);
  }

  @DeleteMapping(
    path = "/{id}/cart/items/{itemId}",
    produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
  )
  @Operation(
    summary = "Delete Item.",
    description = "Deletes an item from the cart. Fails if the provided id does not exist.",
    tags = { "Customer", "Cart" }
  )
  @PreAuthorize("#customerId == principal.id")
  public ResponseEntity<?> deleteItemFromCart(@PathVariable(value = "id") Long customerId, @PathVariable(value = "itemId") Long itemId) {
    manageCartUseCase.deleteItem(customerId, itemId);
    return ResponseEntity.ok().build();
  }

  @PutMapping(
    path = "/{id}/cart/items/{itemId}",
    produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
    consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
  )
  @Operation(
    summary = "Update Item Units.",
    description = "Updates the units of an item of the cart. Fails if the provided id does not exist or if the new unit is not greater than 0.",
    tags = { "Customer", "Cart" }
  )
  @PreAuthorize("#customerId == principal.id")
  public ResponseEntity<CartItemResponse> updateCartItemUnits(@PathVariable(value = "id") Long customerId, @PathVariable(value = "itemId") Long itemId, @RequestBody @Valid UpdateCartItemUnitsRequest request) {
    var newUnits = request.getUnits();
    var item = manageCartUseCase.updateCartItemUnits(customerId, itemId, newUnits);
    var response = cartItemMapper.toCartItemResponse(item).setLinks();
    return ResponseEntity.ok(response);
  }


}
