package com.bensler.bubbles.server.jetty;

import com.bensler.bubbles.entity.Entity;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;

public class RefIdGenerator extends ObjectIdGenerator<Ref> {

  protected final Class<?> _scope;

  public RefIdGenerator() {
      _scope = Entity.class;
  }

  @Override
  public final Class<?> getScope() {
      return _scope;
  }
  
  @Override
  public boolean canUseFor(ObjectIdGenerator<?> gen) {
      return (gen.getClass() == getClass()) && (gen.getScope() == _scope);
  }
  
  public Ref generateId(Object pojo){
    return new Ref(((Entity)pojo).getId());
  }

  @Override
  public ObjectIdGenerator<Ref> forScope(Class<?> scope) {
    return this;
  }

  @Override
  public ObjectIdGenerator<Ref> newForSerialization(Object context) {
    return this;
  }

  @Override
  public IdKey key(Object key) {
    return new IdKey(getClass(), _scope, key) ;
  }

}

