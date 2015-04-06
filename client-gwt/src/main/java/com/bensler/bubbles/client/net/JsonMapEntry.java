package com.bensler.bubbles.client.net;

import com.google.gwt.core.client.JavaScriptObject;

class JsonMapEntry<K, V> extends JavaScriptObject {

  protected JsonMapEntry() {}

  // JSNI getter
  public final native K getKey() /*-{ return this.key; }-*/;
  public final native V getValue() /*-{ return this.value; }-*/;
  
}