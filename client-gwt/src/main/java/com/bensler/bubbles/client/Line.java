package com.bensler.bubbles.client;

import com.bensler.bubbles.client.anim.OpacityAdjustable;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;

public class Line extends Object implements OpacityAdjustable {

  private final Bubble from_;
  private final Bubble to_;
  
  private double opacity_;
  
  public Line(Bubble from, Bubble to) {
    super();
    from_ = from;
    to_ = to;
    opacity_ = 1;
  }

  boolean touches(Entity entity) {
    return from_.getEntity().equals(entity) || to_.getEntity().equals(entity);
  }
  
  @Override
  public void setOpacity(double opacity) {
    opacity_ = opacity;
  }
  
  public void draw(Canvas canvas) {
    final Context2d context = canvas.getContext2d();
    
    context.setLineWidth(2);
    context.setStrokeStyle(BubblesDisplay.COLOR_BUBBLE_BORDER.createCssColor(opacity_));
    context.beginPath();
    context.moveTo(from_.getX(), from_.getY());
    context.lineTo(to_.getX(), to_.getY());
    context.stroke();
  }

  public boolean connectsTo(Bubble bubble) {
    return ((from_ == bubble) || (to_ == bubble));
  }

  @Override
  public String toString() {
    return from_ + "-" + to_;
  }

  @Override
  public int hashCode() {
    return from_.hashCode() + to_.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if ((obj != null) && obj.getClass().equals(obj.getClass())) {
      final Line line = (Line)obj;
      
      return (
        (from_.equals(line.from_) && to_.equals(line.to_))
        || (from_.equals(line.to_) && to_.equals(line.from_))
      );
    }
    return false;
  }

  public Bubble getFrom() {
    return from_;
  }
  
  public Bubble getTo() {
    return to_;
  }
  
}
