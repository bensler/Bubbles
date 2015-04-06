package com.bensler.bubbles.client.anim;

import com.bensler.bubbles.client.Bubble;
import com.bensler.bubbles.client.RelativePosition;
import com.bensler.bubbles.client.RelativePositionImpl;

public abstract class ShapeAnimation extends Object {
  
  public static int linearInterpolation(int a, int b, double progress) {
    return Math.round((float)(a + (progress * (b - a))));
  }
  
  protected ShapeAnimation() {}
  
  protected abstract void onUpdate(double progress);

  protected double interpolate(double progress) {
    return progress;
  }
  
  static abstract class BubbleAnimation extends ShapeAnimation {

    protected final Bubble bubble_;
  
    protected BubbleAnimation(Bubble bubble) {
      bubble_ = bubble;
    }
    
  }
  
  public static class Zoom extends BubbleAnimation {

    private final int oldRadius_;
    protected final int newRadius_;
    
    public Zoom(Bubble bubble, int oldRadius, int newRadius) {
      super(bubble);
      oldRadius_ = oldRadius;
      newRadius_ = newRadius;
    }

    @Override
    protected void onUpdate(double progress) {
      bubble_.resizeTo(linearInterpolation(oldRadius_, newRadius_, progress));
    }

    @Override
    public String toString() {
      return bubble_.getEntity() + "->" + newRadius_;
    }

  }
  
  public static class Move extends BubbleAnimation {

    private final RelativePosition oldPosition_;
    private final RelativePosition newPosition_;
    
    public Move(Bubble bubble, RelativePosition oldPosition) {
      super(bubble);
      oldPosition_ = oldPosition;
      newPosition_ = bubble.getRelativePosition();
    }
    
    @Override
    protected void onUpdate(double progress) {
      bubble_.moveTo(new RelativePositionImpl(
        linearInterpolation(oldPosition_.getDx(), newPosition_.getDx(), progress),
        linearInterpolation(oldPosition_.getDy(), newPosition_.getDy(), progress),
        bubble_.getEntity()
      ));
    }

    @Override
    public String toString() {
      return bubble_.getEntity() + "->" + newPosition_;
    }
    
  }
  
}
