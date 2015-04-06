package com.bensler.bubbles.client.net;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;

class ServerCall<R extends JavaScriptObject> extends Object implements RequestCallback {

  private final ServerConnection app_;
  private final boolean sessionHandling_;

  private final RequestBuilder request_;
  private final String data_;
  private final Success<R> successCallback_;

  ServerCall(
    String url, String data, Success<R> successCallback,
    ServerConnection app, boolean sessionHandling
  ) {
    app_ = app;
    sessionHandling_ = sessionHandling;
    request_ = new RequestBuilder(RequestBuilder.POST, URL.encode(url));
    data_ = data;
    successCallback_ = successCallback;
  }

  @Override
  public void onError(Request request, Throwable exception) {
    exception.printStackTrace();
  }

  @Override
  public void onResponseReceived(Request request, Response response) {
    final int statusCode = response.getStatusCode();

    if (200 == statusCode) {
      final Result result = parseJSon("(" + response.getText() + ")").<Result>cast();

      if ((!result.isLoggedIn()) && (!sessionHandling_)) {
        app_.doLogin();
      } else {
        app_.callFinished(this);
        if (successCallback_ != null) {
          successCallback_.serverCallDone(result.getData().<R>cast());
        }
      }
    } else {
//      app_.doLogin();
      System.out.println("error (status: " + statusCode + ")"); // TODO
    }
  }

  final static native JavaScriptObject parseJSon(String json) /*-{
    return eval(json);
  }-*/;

  void run() {
    try {
      request_.sendRequest(data_, this);
    } catch (RequestException e) {
      e.printStackTrace(); // TODO
    }
  }

  public interface Success<T> {

    public void serverCallDone(T result);

  }

}
