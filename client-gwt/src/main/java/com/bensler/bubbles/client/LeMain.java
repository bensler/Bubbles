package com.bensler.bubbles.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class LeMain extends Object implements EntryPoint {

  public LeMain() {  }

  @Override
  public void onModuleLoad() {
    final Canvas canvas = Canvas.createIfSupported();
    
    if (canvas == null) {
      RootPanel.get("app").add(new Label(
        "Your browser does not support the HTML5 Canvas. Please upgrade your browser to view this demo."
      ));
    } else {
      RootPanel.get("app").add(new App(canvas).getWidget());
    }
  }

}
