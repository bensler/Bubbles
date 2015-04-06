package com.bensler.bubbles.client;

import com.bensler.bubbles.client.anim.OpacityAdjustable;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;

/** Grid lines becoming  visible during animation only */
public class Grid extends Object implements OpacityAdjustable {

  private final static double HALF = 0.5;
  
  private CssColor gridColor_;
  private boolean opaque_;

  public Grid() {
    super();
    setOpacity(0);
  }

  @Override
  public void setOpacity(double opacity) {
    gridColor_ = BubblesDisplay.COLOR_GRID.createCssColor(opacity);
    opaque_ = (opacity > 0.0);
  }

  void draw(Canvas canvas, Bubble centerBubble, int width, int height) {
    final Context2d context = canvas.getContext2d();
    double x = -0.5;
    double y = -0.5;
    
    canvas.setCoordinateSpaceWidth(width);
    canvas.setCoordinateSpaceHeight(height);
    context.setFillStyle("#FFFFFF");
    context.fillRect(0, 0, width, height);
    if (opaque_) {
      final int bubbleRadius = Math.max(1, centerBubble.getRadius());
      final int lineDistance = bubbleRadius * 2;
      
      context.setLineWidth(1);
      context.setStrokeStyle(gridColor_);
      x = centerBubble.getX() - bubbleRadius - HALF;
      do {
        drawVerticalLine(context, x, height);
        x -= lineDistance;
      } while (x > 0);
      x = centerBubble.getX() + bubbleRadius - HALF;
      do {
        drawVerticalLine(context, x, height);
        x += lineDistance;
      } while (x < width);
      y = centerBubble.getX() - bubbleRadius - HALF;
      do {
        drawHorizontalLine(context, y, width);
        y -= lineDistance;
      } while (y > 0);
      y = centerBubble.getX() + bubbleRadius - HALF;
      do {
        drawHorizontalLine(context, y, width);
        y += lineDistance;
      } while (y < width);
    }
  }

  private void drawHorizontalLine(Context2d context, double y, int width) {
    context.beginPath();
    context.moveTo(-1, y);
    context.lineTo(width + 1, y);
    context.closePath();
    context.stroke();
  }

  private void drawVerticalLine(Context2d context, double x, int height) {
    context.beginPath();
    context.moveTo(x, -1);
    context.lineTo(x, height + 1);
    context.closePath();
    context.stroke();
  }

  @Override
  public String toString() {
    return "#";
  }

}
