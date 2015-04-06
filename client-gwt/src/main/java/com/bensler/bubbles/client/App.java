package com.bensler.bubbles.client;

import com.bensler.bubbles.client.net.NetDataSource;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.widgets.layout.HLayout;

public class App extends Object {

  private final BubblesDisplay grid_;
  
  private final DescriptionPanel descriptionPanel_;
  
  private final NetDataSource dataSource_;

  private final HLayout appWidget;
  
  App(Canvas canvas) {
    super();
    dataSource_ = new NetDataSource();
    descriptionPanel_ = new DescriptionPanel();
    grid_ = new BubblesDisplay(canvas, dataSource_, descriptionPanel_);
    Scheduler.get().scheduleDeferred(new ScheduledCommand() {
      @Override
      public void execute() {
        grid_.focus();
      }
    });
    Window.addWindowClosingHandler(new Window.ClosingHandler() {
      @Override
      public void onWindowClosing(ClosingEvent event) {
        if (GWT.isProdMode()) {
          dataSource_.getServerConnection().logout();
        }
      }
    });
    appWidget = new HLayout();
    appWidget.setWidth100();
    appWidget.setHeight100();
    appWidget.addMember(new NativeCanvasAdapter(grid_));
    appWidget.addMember(descriptionPanel_.getPanel());
  }

  public Widget getWidget() {
    return appWidget;
  }

}
