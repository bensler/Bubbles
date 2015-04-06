package com.bensler.bubbles.client.net;


import com.bensler.bubbles.client.net.ServerCall.Success;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;

public class ServerConnection extends Object {

  public final static String DEV_MODE_URL_PREFIX = "/bar/";
  public final static String PROD_MODE_URL_PREFIX = "/bubbles/"; 
  public final static String CONTROLLER_PATH = "Entities/";
  
  private final String urlPrefix_;
  private final LoginCtrl loginCtrl_;

  private ServerCall<?> lastCall_;
	
	public ServerConnection() {
	  super();
	  urlPrefix_ = GWT.isProdMode() ? PROD_MODE_URL_PREFIX : DEV_MODE_URL_PREFIX;
	  loginCtrl_ = new LoginCtrl(this);
	}
	
	public <R extends JavaScriptObject> void callServer(
	  String method, Object arg, JSONObject data, Success<R> successCallback
  ) {
	  callServer(method, arg, data, false, successCallback);
	}
	
  <R extends JavaScriptObject> void callServer(
    String method, Object arg, JSONObject args, boolean sessionHandling, Success<R> successCallback
  ) {
    final String url = GWT.getHostPageBaseURL();
    final String baseUrl = url.substring(0, url.length() - getPathFromUrl(url).length());
    final ServerCall<R> serverCall;
    final String argStr = ((arg == null) ? "" : (arg.toString() + ".json"));

    args = ((args == null) ? new JSONObject() : args);
    serverCall = new ServerCall<R>(
      baseUrl + urlPrefix_ + CONTROLLER_PATH + method + "/" + argStr,
      args.toString(), successCallback, this, sessionHandling
    );
    if (!sessionHandling) {
      lastCall_ = serverCall;
    }
    serverCall.run();
  }
  
  void callFinished(ServerCall<?> serverCall) {
    if (lastCall_ == serverCall) {
      lastCall_ = null;
    }
  }
  
  final static native String getPathFromUrl(String url) /*-{
    var l = document.createElement("a");
    l.href = url;
    return l.pathname;
  }-*/;
  
  void setLoggedInUser(User user) {
    if ((user != null) && (lastCall_ != null)) {
      lastCall_.run();
    }
  }

  void doLogin() {
    loginCtrl_.login();  
  }

  public void logout() {
    loginCtrl_.logout();
  }

}
