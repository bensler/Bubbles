package com.bensler.bubbles.client.anim;


public class FlashAnimation extends ShapeAnimation {
  
  private final static double r = 0.5;
  private final static double rSquare = r * r;
  
  protected final OpacityAdjustable shape_;
  
  public FlashAnimation(OpacityAdjustable shape) {
    shape_ = shape;
    shape.setOpacity(0);
  }
  
  @Override
  public double interpolate(double progress) {
    progress -= r;
    return Math.sqrt(rSquare - (progress * progress));
  }

  protected void onUpdate(double progress) {
    shape_.setOpacity(progress);
  }

  @Override
  public String toString() {
    return "<<" + shape_ + ">>";
  }

}
