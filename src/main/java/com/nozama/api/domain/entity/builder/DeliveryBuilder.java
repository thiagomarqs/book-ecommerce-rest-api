package com.nozama.api.domain.entity.builder;

import com.nozama.api.domain.entity.Address;
import com.nozama.api.domain.entity.Delivery;
import com.nozama.api.domain.entity.Order;
import com.nozama.api.domain.enums.DeliveryStatus;
import com.nozama.api.domain.util.DomainUtils;

public class DeliveryBuilder {

  private Delivery delivery = new Delivery();

  public static DeliveryBuilder builder() {
    return new DeliveryBuilder();
  }

  public DeliveryBuilder withOrder(Order order) {
    delivery.setOrder(order);
    return this;
  }

  public DeliveryBuilder withAddress(Address address) {
    delivery.setAddress(address);
    return this;
  }

  public Delivery build() {
    delivery.setCode(DomainUtils.generateDeliveryCode());
    delivery.setStatus(DeliveryStatus.PENDING);
    return delivery;
  }

}
