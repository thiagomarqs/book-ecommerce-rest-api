package com.nozama.api.application.dto.response.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.hateoas.RepresentationModel;

import com.nozama.api.domain.enums.OrderStatus;

public class OrderResponse extends RepresentationModel<OrderResponse> {
  private Long id;
  private Set<OrderItemResponse> items;
  private String code;
  private OrderStatus status;
  private BigDecimal total;
  private LocalDateTime createdAt;
  private LocalDateTime finishedAt;
  
  
  public Set<OrderItemResponse> getItems() {
    return items;
  }
  public void setItems(Set<OrderItemResponse> items) {
    this.items = items;
  }
  public String getCode() {
    return code;
  }
  public void setCode(String code) {
    this.code = code;
  }
  public OrderStatus getStatus() {
    return status;
  }
  public void setStatus(OrderStatus status) {
    this.status = status;
  }
  public BigDecimal getTotal() {
    return total;
  }
  public void setTotal(BigDecimal total) {
    this.total = total;
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
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }

}
