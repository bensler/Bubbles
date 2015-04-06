package com.bensler.bubbles.client;

import com.bensler.bubbles.client.anim.OpacityAdjustable;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;

/** Represents a displayed {@link Entity} */
public class Bubble extends Object implements OpacityAdjustable {

  private final EntityImpl entity_;
  private final BubblesAndLines model_;
  private int radius_;
  
  private CssColor colorFill;
  private CssColor colorBorder;
  
  Bubble(
    EntityImpl entity, BubblesAndLines model, int radius
  ) {
    super();
    colorFill = BubblesDisplay.COLOR_BUBBLE_FILL.createCssColor(1);
    colorBorder = BubblesDisplay.COLOR_BUBBLE_BORDER.createCssColor(1);
    entity_ = entity;
    model_ = model;
    radius_ = radius;
  }

  public EntityImpl getEntity() {
    return entity_;
  }

  public int getX() {
    return model_.getCenterX() + getRelativePosition().getDx();
  }

  public RelativePositionImpl getRelativePosition() {
    return model_.getRelativePosition(this);
  }

  public int getY() {
    return model_.getCenterY() + getRelativePosition().getDy();
  }

  int getRadius() {
    return radius_;
  }

  @Override
  public void setOpacity(double opacity) {
    colorFill = BubblesDisplay.COLOR_BUBBLE_FILL.createCssColor(opacity);
    colorBorder = BubblesDisplay.COLOR_BUBBLE_BORDER.createCssColor(opacity);
  }

  void draw(Canvas canvas) {
    final Context2d context = canvas.getContext2d();
    final String entityName = entity_.getName();
    final int x = getX();
    final int y = getY();

    context.setLineWidth(1);
    context.setFillStyle(colorFill);
    context.beginPath();
    context.arc(x, y, radius_ + 0.5, 0, 6.3);
    context.closePath();
    context.fill();
    context.setStrokeStyle(colorBorder);
    context.stroke();
    final double nameWidth = context.measureText(entityName).getWidth();
    
	context.strokeText(entityName, x - (nameWidth / 2), y);
  }
  
  boolean hit(int x, int y) {
    final int xDiff = (getX() - x);
    final int yDiff = (getY() - y);

    return Math.sqrt((xDiff * xDiff) + (yDiff * yDiff)) < radius_;
  }

  @Override
  public String toString() {
    return "(" + entity_.toString() + ")";
  }

  @Override
  public int hashCode() {
    return entity_.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return getClass().equals(obj.getClass()) && entity_.equals(((Bubble)obj).entity_);
  }

  public void moveTo(RelativePositionImpl position) {
    model_.moveTo(this, position);
  }

  public void resizeTo(int newR) {
    radius_ = newR;
  }

}