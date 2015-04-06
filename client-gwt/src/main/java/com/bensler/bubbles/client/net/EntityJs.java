package com.bensler.bubbles.client.net;

import com.bensler.bubbles.client.Entity;
import com.google.gwt.core.client.JavaScriptObject;

/** Represents a certain concept */
public class EntityJs extends JavaScriptObject implements Entity {

  protected EntityJs() {}

  // JSNI getter
  @Override
  public final native Integer getId() /*-{ return this.id == null ? null : @java.lang.Integer::valueOf(I)(this.id); }-*/;
  @Override
  public final native String getName() /*-{ return this.name; }-*/;
  @Override
  public final native String getDescription() /*-{ return this.description; }-*/;

}
