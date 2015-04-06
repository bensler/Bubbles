package com.bensler.bubbles.server.jetty;

public class Ref extends Object {

  private final Long id_;
  
  public Ref(Long id) {
    id_ = id;
  }

  public Long getId() {
    return id_;
  }
  
}
