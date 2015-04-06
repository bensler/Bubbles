package com.bensler.bubbles.client.net;

import com.bensler.bubbles.client.RelativePosition;
import com.google.gwt.core.client.JavaScriptObject;

public class RelativePositionJs extends JavaScriptObject implements RelativePosition {

  protected RelativePositionJs() {}

  // JSNI getter
  @Override
  public final native Integer getId() /*-{ return this.id == null ? null : @java.lang.Integer::valueOf(I)(this.id); }-*/;
  @Override
  public final native int getDx() /*-{ return this.dx; }-*/;
  @Override
  public final native int getDy() /*-{ return this.dy; }-*/;
  
  @Override
  public final native EntityJs getRelatedEntity() /*-{
    return this.relatedEntity;
  }-*/;

}
