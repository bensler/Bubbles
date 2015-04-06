package com.bensler.bubbles.client.net;

import java.util.HashMap;

import com.bensler.bubbles.client.DataProvider;
import com.bensler.bubbles.client.DisplayMap;
import com.bensler.bubbles.client.Entity;
import com.bensler.bubbles.client.EntityImpl;
import com.bensler.bubbles.client.RelativePositionImpl;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.json.client.JSONNumber;

public class NetDataSource extends Object implements DataProvider {
  
  public static interface DisplayMapCallback {
  
    public void displayMapReceived(DisplayMap displayMap);
    
  }
  
  private final HashMap<Entity, DisplayMap> displayMaps_;
  private final ServerConnection srv_;
  
  public NetDataSource() {
    displayMaps_ = new HashMap<Entity, DisplayMap>(10);
    srv_ = new ServerConnection();
  }

  public ServerConnection getServerConnection() { 
    return srv_;
  }
  
  private void loadDisplayMap(String methodName, Integer entityId, final DisplayMapCallback displayMapCallback) {
    srv_.callServer(methodName, entityId, null, new ServerCall.Success<DisplayMapJs>() {

      public void serverCallDone(DisplayMapJs beData) {
        final EntityImpl entity = new EntityImpl(beData.getEntity());
        final DisplayMap displayMap = new DisplayMap(entity);
        final JsArray<RelativePositionJs> relatedEntities = beData.getRelatedEntities();
        
        for (int i = 0; i < relatedEntities.length(); i++) {
          final RelativePositionJs relativePosition = relatedEntities.get(i);
          
          displayMap.putPosition(
            new RelativePositionImpl(
              relativePosition.getDx(), relativePosition.getDy(),
              new EntityImpl(relativePosition.getRelatedEntity())
            )
          );
        }
        displayMaps_.put(entity, displayMap);
        displayMapCallback.displayMapReceived(displayMap);
      }
      
    });
  }

  @Override
  public void getDisplayMap(Entity entity, DisplayMapCallback displayMapCallback) {
    if (entity == null) {
      loadDisplayMap("getInitialDisplayMap", null, displayMapCallback);
    } else {
      if (displayMaps_.containsKey(entity)) {
        displayMapCallback.displayMapReceived(displayMaps_.get(entity));
      } else {
        loadDisplayMap("getDisplayMap", entity.getId(), displayMapCallback);
      }
    }
  }

  @Override
  public void saveRelativePosition(
    EntityImpl focusedEntity, final EntityImpl relatedEntity, RelativePositionImpl position
  ) {
    final DisplayMap displayMap = displayMaps_.get(focusedEntity);
    
    if (displayMap != null) {
      displayMap.updatePosition(relatedEntity, position);
      srv_.callServer(
        "saveNodePosition", focusedEntity.getId(), 
        JsonProperty.makeObject(
          new JsonProperty("targetEntity", new JSONNumber(relatedEntity.getId())),
          new JsonProperty("RelativePosition", JsonProperty.makeObject(
            new JsonProperty("dx", new JSONNumber(position.getDx())),
            new JsonProperty("dy", new JSONNumber(position.getDy()))
          ))
        ),
        new ServerCall.Success<RelativePositionJs>() {
          public void serverCallDone(RelativePositionJs position) {
            displayMap.updatePosition(relatedEntity, new RelativePositionImpl(
              // TODO update local EntityImpl from positions relatedEntity
              position.getDx(), position.getDy(), relatedEntity
            ));
          }
        }
      );
    }
  }

}