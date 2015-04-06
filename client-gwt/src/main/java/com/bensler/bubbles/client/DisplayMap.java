package com.bensler.bubbles.client;

import java.util.LinkedHashMap;

public class DisplayMap extends Object {
  
  private final EntityImpl entity_;
  private final LinkedHashMap<EntityImpl, RelativePositionImpl> relativePositions_;

  public DisplayMap(EntityImpl entity) {
    super();
    entity_ = entity;
    relativePositions_ = new LinkedHashMap<EntityImpl, RelativePositionImpl>();
  }

  public EntityImpl getEntity() {
    return entity_;
  }

  public void putPosition(RelativePositionImpl position) {
    relativePositions_.put(position.getRelatedEntity(), position);
  }

  Iterable<EntityImpl> getRelatedEntities() {
    return relativePositions_.keySet();
  }
  
  RelativePositionImpl getPosition(Entity entity) {
    return relativePositions_.get(entity);
  }

  public void updatePosition(EntityImpl entity, RelativePositionImpl position) {
    if (relativePositions_.containsKey(entity)) {
      relativePositions_.put(entity, position);
    }
  }

  public void setPosition(EntityImpl entity, RelativePositionImpl position) {
    final RelativePosition oldPosition = relativePositions_.get(entity);
    
    if (oldPosition != null) {
      relativePositions_.put(entity, position);
    }
  }
  
}
