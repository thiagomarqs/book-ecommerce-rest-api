package com.nozama.api.application.dto.request.book;

public class BookActiveStatusRequest {
  
  private Boolean active;

  public BookActiveStatusRequest() {}
  
  public BookActiveStatusRequest(Boolean active) {
    this.active = active;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }
  
}
