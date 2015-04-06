package com.bensler.bubbles.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "entity", "relativePositions" })
public class DisplayMap extends Object {
  
  private final Entity entity_;
  private final List<RelativePosition> relativePositions_;

  public DisplayMap(Entity entity) {
    super();
    entity_ = entity;
    relativePositions_ = new ArrayList<RelativePosition>();
  }

  public Entity getEntity() {
    return entity_;
  }

  public void putPosition(RelativePosition position) {
    relativePositions_.add(position);
  }

  public List<RelativePosition> getRelatedEntities() {
    return relativePositions_;
  }

}
