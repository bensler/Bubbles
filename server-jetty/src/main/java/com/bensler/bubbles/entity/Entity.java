package com.bensler.bubbles.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;

@JsonIdentityInfo(generator=com.bensler.bubbles.server.jetty.RefIdGenerator.class, property="xid")
public class Entity {

  private final Long id_;

  private final String name_;
  private final String description_;

  Entity() {
    this("", null);
  }

  public Entity(final String name, final String description) {
    id_ = null;
    name_ = name;
    description_ = description;
  }

  public Long getId() {
    return id_;
  }

  public String getName() {
    return name_;
  }

  public String getDescription() {
    return ((description_ != null) ? description_ : "");
  }

  @Override
  public String toString() {
    return "[" + getId() + "]" + name_;
  }

}
