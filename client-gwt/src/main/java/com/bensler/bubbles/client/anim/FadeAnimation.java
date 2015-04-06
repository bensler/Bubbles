package com.bensler.bubbles.client.anim;


public class FadeAnimation extends ShapeAnimation {
  
  protected final OpacityAdjustable shape_;
  protected final boolean appear_;
  
  public FadeAnimation(OpacityAdjustable shape, boolean appear) {
    shape_ = shape;
    appear_ = appear;
    shape.setOpacity(appear ? 0 : 1);
  }
  
  protected void onUpdate(double progress) {
    shape_.setOpacity(
      appear_
      ? progress
      : ((-1 * progress) + 1)
    );
  }

  @Override
  public String toString() {
    return (appear_ ? "" : shape_) + "->" + (appear_ ? shape_ : "");
  }

}
