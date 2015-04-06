package com.bensler.bubbles.server.jetty;

class Result extends Object {

  private final Object data_;
  
  Result(Object data) {
    data_ = data;
  }

  public Object getData() {
    return data_;
  }

  public boolean isLoggedIn() {
    return true;
  }
  
}