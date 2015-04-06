package com.bensler.bubbles.client;

import com.bensler.bubbles.client.net.NetDataSource.DisplayMapCallback;


public interface DataProvider {

  public void getDisplayMap(Entity entity, DisplayMapCallback displayMapCallback);

  public void saveRelativePosition(
    EntityImpl focusedEntity, EntityImpl relatedEntity, 
    RelativePositionImpl relativePosition
  );

}