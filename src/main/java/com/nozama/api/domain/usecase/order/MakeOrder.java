package com.nozama.api.domain.usecase.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nozama.api.application.dto.request.order.OrderRequest;
import com.nozama.api.domain.entity.Address;
import com.nozama.api.domain.entity.CartItem;
import com.nozama.api.domain.entity.Customer;
import com.nozama.api.domain.entity.Order;
import com.nozama.api.domain.entity.Payment;
import com.nozama.api.domain.entity.PaymentMethod;
import com.nozama.api.domain.entity.builder.DeliveryBuilder;
import com.nozama.api.domain.entity.builder.OrderBuilder;
import com.nozama.api.domain.enums.PaymentStatus;
import com.nozama.api.domain.repository.BookRepository;
import com.nozama.api.domain.repository.DeliveryRepository;
import com.nozama.api.domain.repository.OrderRepository;
import com.nozama.api.domain.repository.PaymentRepository;
import com.nozama.api.domain.usecase.cart.ManageCart;
import com.nozama.api.domain.usecase.customer.ManageCustomer;

@Component
public class MakeOrder {
  
  @Autowired
  private ManageCustomer manageCustomerUseCase;

  @Autowired
  private ManageCart manageCartUseCase;

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private PaymentRepository paymentRepository;

  @Autowired
  private DeliveryRepository deliveryRepository;

  public Order execute(Long customerId, OrderRequest request) {
    var customer = manageCustomerUseCase.find(customerId);
    var paymentMethod = customer.getPaymentMethod(request.getPaymentMethodId());
    var address = customer.getAddress(request.getAddressId());
    var cart = customer.getCartItems();
    
    var order = issueOrder(customer, cart);

    issuePayment(paymentMethod, order);

    issueDelivery(address, order);

    manageCartUseCase.emptyCart(customerId);
    
    decreaseBooksQuantity(cart);

    return order;
  }

  private Order issueOrder(Customer customer, List<CartItem> cart) {
    var order = OrderBuilder
      .builder()
      .withCustomer(customer)
      .withCartItems(cart)
      .build();

    order = orderRepository.save(order);
    return order;
  }

  private void issuePayment(PaymentMethod paymentMethod, Order order) {
    var payment = new Payment(order, paymentMethod, order.getTotal(), PaymentStatus.APPROVED);

    payment = paymentRepository.save(payment);
  }

  private void issueDelivery(Address address, Order order) {
    var delivery = DeliveryBuilder
      .builder()
      .withOrder(order)
      .withAddress(address)
      .build();

    deliveryRepository.save(delivery);
  }

  private void decreaseBooksQuantity(List<CartItem> cart) {
    cart.forEach(item -> {
      var book = item.getBook();
      var units = item.getUnits();
      bookRepository.decreaseAvailableQuantity(book.getId(), units);
    });
  }

}
