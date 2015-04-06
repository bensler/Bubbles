package com.bensler.bubbles.client;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.WidgetCanvas;
import com.smartgwt.client.widgets.events.ResizedEvent;
import com.smartgwt.client.widgets.events.ResizedHandler;
import com.smartgwt.client.widgets.events.VisibilityChangedEvent;
import com.smartgwt.client.widgets.events.VisibilityChangedHandler;

public class NativeCanvasAdapter extends WidgetCanvas {

  private final BubblesDisplay display_;

  public NativeCanvasAdapter(final BubblesDisplay display) {
    super(display.getCanvas());
    display_ = display;

    final WidgetCanvas outerThis = this;
    addResizedHandler(new ResizedHandler() {
      public void onResized(final ResizedEvent event) {
        display_.setSize(
          outerThis.getWidth(),
          outerThis.getHeight()
        );
      }
    });
    outerThis.setOverflow(Overflow.HIDDEN);

    addVisibilityChangedHandler(new VisibilityChangedHandler() {
      @Override
      public void onVisibilityChanged(final VisibilityChangedEvent event) {
        display.getCanvas().setVisible(event.getIsVisible());
      }
    });
    setHeight100();
    setWidth100();
  }

}