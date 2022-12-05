package com.nozama.api.application.dto.request.publisher;

public class PublisherActiveStatusRequest {
  
  private Boolean active;

  public PublisherActiveStatusRequest() {}
  
  public PublisherActiveStatusRequest(Boolean active) {
    this.active = active;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }
  
}
