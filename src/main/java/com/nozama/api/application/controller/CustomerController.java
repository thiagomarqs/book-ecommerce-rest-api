package com.nozama.api.application.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
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
import com.nozama.api.application.dto.request.order.OrderRequest;
import com.nozama.api.application.dto.request.paymentMethod.PaymentMethodRequest;
import com.nozama.api.application.dto.response.address.AddressResponse;
import com.nozama.api.application.dto.response.cartItem.CartItemResponse;
import com.nozama.api.application.dto.response.order.OrderResponse;
import com.nozama.api.application.dto.response.paymentMethod.PaymentMethodResponse;
import com.nozama.api.application.mapper.AddressMapper;
import com.nozama.api.application.mapper.CartItemMapper;
import com.nozama.api.application.mapper.EntityMapper;
import com.nozama.api.domain.entity.Address;
import com.nozama.api.domain.entity.PaymentMethod;
import com.nozama.api.domain.usecase.book.ManageBook;
import com.nozama.api.domain.usecase.cart.ManageCart;
import com.nozama.api.domain.usecase.customer.ManageCustomerAddresses;
import com.nozama.api.domain.usecase.customer.ManageCustomerPaymentMethods;
import com.nozama.api.domain.usecase.order.MakeOrder;
import com.nozama.api.domain.usecase.order.ManageOrder;

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
  private ManageCustomerPaymentMethods manageCustomerPaymentMethodsUseCase;

  @Autowired
  private EntityMapper entityMapper;

  @Autowired
  private AddressMapper addressMapper;

  @Autowired
  private CartItemMapper cartItemMapper;

  @Autowired
  private MakeOrder makeOrderUseCase;

  @Autowired
  private ManageOrder manageOrderUseCase;

  private Link getCustomerLink(Long customerId) {
    return Link.of("/api/customers/{id}", "customer").expand(customerId);
  }



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
  public ResponseEntity<CollectionModel<AddressResponse>> findAddresses(@PathVariable(value = "id") Long customerId) {
    final var customerAddresses = manageCustomerAddressesUseCase.getAddresses(customerId);
    final var list = entityMapper.mapList(customerAddresses, AddressResponse.class);
    Link[] links = {
      Link.of("/api/customers/{id}/addresses").expand(customerId),
      getCustomerLink(customerId),
      Link.of(String.format("/api/customers/%d/addresses/{addressId}", customerId), "customerAddresses")
    };

    var response = CollectionModel.of(list, links);
    
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
    final var address = manageCustomerAddressesUseCase.find(customerId, addressId);
    final var response = entityMapper.mapEntity(address, AddressResponse.class).setLinks();

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
  public ResponseEntity<CollectionModel<AddressResponse>> addAddress(@PathVariable(value = "id") Long customerId, @RequestBody AddressCreateRequest addressRequest) {
    final var address = entityMapper.mapEntity(addressRequest, Address.class);
    final var addresses = manageCustomerAddressesUseCase.addAddress(customerId, address);
    final var list = entityMapper.mapList(addresses, AddressResponse.class);
    final Link[] links = {
      Link.of("/api/customers/{id}/addresses").expand(customerId),
      getCustomerLink(customerId),
      Link.of(String.format("/api/customers/%d/addresses/{addressId}", customerId), "customerAddresses")
    };
    final var response = CollectionModel.of(list, links);
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
  public ResponseEntity<CollectionModel<AddressResponse>> updateAddress(@PathVariable(value = "id") Long customerId, @PathVariable(value = "addressId") Long addressId, @RequestBody AddressUpdateRequest addressRequest) {
    final var address = entityMapper.mapEntity(addressRequest, Address.class);
    address.setId(addressId);    

    final var addresses = manageCustomerAddressesUseCase.updateAddress(customerId, address);
    final var list = entityMapper.mapList(addresses, AddressResponse.class);
    final Link[] links = {
      Link.of("/api/customers/{id}/addresses").expand(customerId),
      getCustomerLink(customerId),
      Link.of(String.format("/api/customers/%d/addresses/{addressId}", customerId), "customerAddresses")
    };
    final var response = CollectionModel.of(list, links);

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
  public ResponseEntity<CollectionModel<CartItemResponse>> getCart(@PathVariable(value = "id") Long customerId) {
    final var cart = manageCartUseCase.getCart(customerId);
    final var list = entityMapper.mapList(cart, CartItemResponse.class);
    final Link[] links = {
      getCustomerLink(customerId),
      Link.of("/api/customers/{id}/cart", "cart").expand(customerId),
      Link.of(String.format("/api/customers/%d/cart/items/{itemId}", customerId), "cartItem")
    };
    final var response = CollectionModel.of(list, links);
    
    return ResponseEntity.ok(response);
  }

  @GetMapping(
    path = "/{id}/cart/items/{itemId}",
    produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
  )
  @Operation(
    summary = "Get one Item.",
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
  public ResponseEntity<CollectionModel<CartItemResponse>> addItemToCart(@PathVariable(value = "id") Long customerId, @RequestBody CartItemRequest request) {
    final var book = manageBookUseCase.findById(request.getBookId());
    final var item = cartItemMapper.toCartItem(request, book);
    final var cart = manageCartUseCase.addItem(customerId, item);
    final var list = entityMapper.mapList(cart, CartItemResponse.class);
    final Link[] links = {
      getCustomerLink(customerId),
      Link.of("/api/customers/{id}/cart", "cart").expand(customerId),
      Link.of(String.format("/api/customers/%d/cart/items/{itemId}", customerId), "cartItem")
    };
    final var response = CollectionModel.of(list, links);
    final var uri = Link.of("/api/customers/{id}/cart").expand(customerId).toUri();

    return ResponseEntity.created(uri).body(response);
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



  @PostMapping(
    path = "/{id}/paymentMethods",
    consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
  )
  @Operation(
    summary = "Add Payment Method.",
    description = "Add a new payment method to the customer account.",
    tags = { "Customer", "Payments" }
  )
  @PreAuthorize("#customerId == principal.id")
  public ResponseEntity<?> addPaymentMethod(@PathVariable(name = "id") Long customerId, @RequestBody PaymentMethodRequest request) {
    var paymentMethod = entityMapper.mapEntity(request, PaymentMethod.class);
    
    manageCustomerPaymentMethodsUseCase.add(customerId, paymentMethod);
    
    final var uri = Link.of("/api/customers/{id}/paymentMethods").expand(customerId).toUri();
    
    return ResponseEntity.created(uri).body(null);
  }

  @DeleteMapping(
    path = "/{id}/paymentMethods/{paymentMethodId}"
  )
  @Operation(
    summary = "Delete Payment Method.",
    description = "Deletes a payment method from the customer account.",
    tags = { "Customer", "Payments" }
  )
  @PreAuthorize("#customerId == principal.id")
  public ResponseEntity<?> deletePaymentMethod(@PathVariable(name = "id") Long customerId, @PathVariable(name = "paymentMethodId") UUID paymentMethodId) {
    manageCustomerPaymentMethodsUseCase.delete(customerId, paymentMethodId);
    return ResponseEntity.ok().build();
  }

  @GetMapping(
    path = "/{id}/paymentMethods",
    produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
  )
  @Operation(
    summary = "Get All Payment Methods.",
    description = "Gets all payment methods from the customer account.",
    tags = { "Customer", "Payments" }
  )
  @PreAuthorize("#customerId == principal.id")
  public ResponseEntity<CollectionModel<PaymentMethodResponse>> getPaymentMethods(@PathVariable(name = "id") Long customerId) {
    final var methods = manageCustomerPaymentMethodsUseCase.getAll(customerId);
    final var list = entityMapper.mapList(methods, PaymentMethodResponse.class);
    final Link[] links = {
      getCustomerLink(customerId),
      Link.of("/api/customers/{id}/paymentMethods").expand(customerId)
    };
    final var response = CollectionModel.of(list, links);

    return ResponseEntity.ok(response);
  }



  @GetMapping(
    path = "/{id}/orders",
		produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
  )
  @Operation(
    summary = "All orders",
    description = "Finds all orders of a customer.",
    tags = { "Customer", "Orders" }
  )
  @PreAuthorize("#customerId == principal.id")
  public ResponseEntity<CollectionModel<OrderResponse>> allOrders(@PathVariable(name = "id") Long customerId) { 
    final var orders = manageOrderUseCase.findAll(customerId);
    final var list = entityMapper.mapList(orders, OrderResponse.class);
    final Link[] links = {
      getCustomerLink(customerId),
      Link.of("/api/customers/{id}/orders").expand(customerId)
    };
    final var response = CollectionModel.of(list, links);

    return ResponseEntity.ok(response);
  }

  @GetMapping(
    path = "/{id}/orders/{orderId}",
		produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
  )
  @Operation(
    summary = "Find order",
    description = "Finds an order by its id",
    tags = { "Customer", "Orders" }
  )
  @PreAuthorize("#customerId == principal.id")
  public ResponseEntity<OrderResponse> find(@PathVariable(name = "id") Long customerId, @PathVariable(name = "orderId") Long orderId) { 
    final var order = manageOrderUseCase.find(customerId, orderId);
    final Link[] links = {
      getCustomerLink(customerId),
      Link.of("/api/customers/{id}/orders").expand(customerId),
      Link.of("/api/customers/{id}/orders/{orderId}").expand(customerId, order.getId())
    };
    final var response = entityMapper.mapEntity(order, OrderResponse.class).add(links);

    return ResponseEntity.ok(response);
  }

  @PostMapping(
    path = "/{id}/orders",
		consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
		produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
  )
  @Operation(
    summary = "New order",
    description = "Makes a new order for the customer. All the items present in the cart will be included in the order. The request requires the payment method (yes, I know that in a real scenario this would be different) and the address. If, for some reason, making the order was not possible, a relevant error will be provided.",
    tags = { "Customer", "Orders" }
  )
  @PreAuthorize("#customerId == principal.id")
  public ResponseEntity<OrderResponse> newOrder(@PathVariable(name = "id") Long customerId, @RequestBody OrderRequest request) { 
    final var order = makeOrderUseCase.execute(customerId, request);
    final Link[] links = {
      getCustomerLink(customerId),
      Link.of("/api/customers/{id}/orders", "orders").expand(customerId),
      Link.of("/api/customers/{id}/orders/{orderId}").expand(customerId, order.getId())
    };
    final var response = entityMapper.mapEntity(order, OrderResponse.class).add(links);
    final var uri = Link.of("/api/customers/{id}/orders/{orderId}").expand(customerId, order.getId()).toUri();

    return ResponseEntity.created(uri).body(response);
  }



}