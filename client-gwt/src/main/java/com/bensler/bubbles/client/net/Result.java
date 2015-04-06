package com.bensler.bubbles.client.net;

import com.google.gwt.core.client.JavaScriptObject;

class Result extends JavaScriptObject {

  protected Result() {}

  // JSNI getter
  public final native JavaScriptObject getData() /*-{ return this.data; }-*/;
  public final native JavaScriptObject getRequest() /*-{ return this.request; }-*/;
  public final native boolean isLoggedIn() /*-{ return this.loggedIn; }-*/;
  
}