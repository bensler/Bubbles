package com.bensler.bubbles.client.anim;

import java.util.ArrayList;

import com.bensler.bubbles.client.BubblesDisplay;
import com.google.gwt.animation.client.Animation;

public class Animateuse extends Animation {

  private final int animationDuration_;
  private final BubblesDisplay display_;
  
  private final ArrayList<ShapeAnimation> animations_;

  private boolean animating_;
  
  public Animateuse(BubblesDisplay grid, int ms) {
    super();
    animationDuration_ = ms;
    display_ = grid;
    animations_ = new ArrayList<ShapeAnimation>();
  }

  public void clearAnimations() {
    animations_.clear();
  }
  
  public boolean addAnimation(ShapeAnimation animation) {
    return animations_.add(animation);
  }

  public void start() {
    animating_ = true;
    run(animationDuration_);
  }

  public boolean isAnimating() {
    return animating_;
  }

  @Override
  protected void onUpdate(double progress) {
    for (ShapeAnimation animation : animations_) {
      animation.onUpdate(animation.interpolate(progress));
    }
    display_.draw();
  }

  @Override
  protected void onComplete() {
    super.onComplete();
    display_.animationFinished();
    animating_ = false;
  }

}