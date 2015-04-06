package com.bensler.bubbles.client;

import com.bensler.bubbles.client.net.EntityJs;

/** Represents a certain concept */
public class EntityImpl implements Entity {

  private final EntityJs entity_;
  
  public EntityImpl(EntityJs entity) {
    super();
    entity_ = entity;
  }

  @Override
  public Integer getId() {
    return entity_.getId();
  }

  @Override
  public String getName() {
    return entity_.getName();
  }

  @Override
  public String getDescription() {
    return entity_.getDescription();
  }

  @Override
  public String toString() {
    return getName();
  }

  @Override
  public int hashCode() {
    final Integer id = getId();
    
    return ((id == null) ? 17 : id);
  }

  @Override
  public boolean equals(Object obj) {
    return (
      (obj != null)
      && getClass().equals(obj.getClass()) 
      && (getId() == ((Entity)obj).getId())
    );
  }

}
