package com.bensler.bubbles.client.net;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class DisplayMapJs extends JavaScriptObject {

  protected DisplayMapJs() {}

  // JSNI getter
//  @Override
  public final native EntityJs getEntity() /*-{ return this.entity; }-*/;
//  @Override
  public final native JsArray<RelativePositionJs> getRelatedEntities() /*-{
    return this.relatedEntities;
  }-*/;

}
