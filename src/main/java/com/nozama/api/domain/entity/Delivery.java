package com.nozama.api.domain.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.nozama.api.domain.enums.DeliveryStatus;

@Entity
public class Delivery {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @NotBlank
  @Size(min = 20, max = 20)
  private String code;

  @OneToOne
  private Order order;

  @OneToOne
  private Address address;

  @Enumerated(EnumType.STRING)
  private DeliveryStatus status;

  private LocalDateTime createdAt = LocalDateTime.now();
  private LocalDateTime finishedAt;

  public Delivery() {}
  
  public Delivery(String code, Order order, Address address, DeliveryStatus status) {
    this.code = code;
    this.order = order;
    this.address = address;
    this.status = status;
  }
  
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getCode() {
    return code;
  }
  public void setCode(String code) {
    this.code = code;
  }
  public Order getOrder() {
    return order;
  }
  public void setOrder(Order order) {
    this.order = order;
  }
  public Address getAddress() {
    return address;
  }
  public void setAddress(Address address) {
    this.address = address;
  }
  public DeliveryStatus getStatus() {
    return status;
  }
  public void setStatus(DeliveryStatus status) {
    this.status = status;
  }
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
  public LocalDateTime getFinishedAt() {
    return finishedAt;
  }
  public void setFinishedAt(LocalDateTime finishedAt) {
    this.finishedAt = finishedAt;
  }
  
  
}
