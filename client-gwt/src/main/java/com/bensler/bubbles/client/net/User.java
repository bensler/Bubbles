package com.bensler.bubbles.client.net;

import com.google.gwt.core.client.JavaScriptObject;

class User extends JavaScriptObject {

  protected User() {}

  // JSNI getter
  public final native String getId() /*-{ return this.id; }-*/;
  public final native String getUsername() /*-{ return this.username; }-*/;
  public final native String getName() /*-{ return this.name; }-*/;
  
}