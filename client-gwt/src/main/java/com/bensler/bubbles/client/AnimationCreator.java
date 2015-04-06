package com.bensler.bubbles.client;

import com.bensler.bubbles.client.anim.Animateuse;
import com.bensler.bubbles.client.anim.FadeAnimation;
import com.bensler.bubbles.client.anim.ShapeAnimation;

/** Creates a set of {@link ShapeAnimation}s out of the differences of
 * two given models. */
public class AnimationCreator {

  AnimationCreator () {}
  
  void createAnimation(
    BubblesAndLines newModel, BubblesAndLines oldModel, Animateuse animateuse
  ) {
    for (Entity entity : newModel.getEntities()) {
      final Bubble bubble = newModel.getBubble(entity);
      final Bubble oldBubble = oldModel.getBubble(entity);
      
      if (oldBubble == null) {
        // let new bubble appear
        animateuse.addAnimation(new ShapeAnimation.Zoom(bubble, 0, bubble.getRadius()));
        animateuse.addAnimation(new FadeAnimation(bubble, true));
        // let its lines show up
        for (Line line : newModel.getLinesOf(entity)) {
          animateuse.addAnimation(new FadeAnimation(line, true));
        }
      } else {
        // move already visible bubble
        animateuse.addAnimation(new ShapeAnimation.Move(bubble, oldBubble.getRelativePosition()));
        if (newModel.getCenterEntity() == entity) {
          // zoom new center bubble
          animateuse.addAnimation(new ShapeAnimation.Zoom(bubble, oldBubble.getRadius(), bubble.getRadius()));
        } else if (entity.equals(oldModel.getCenterEntity())) {
          // zoom out old center bubble
          animateuse.addAnimation(new ShapeAnimation.Zoom(bubble, oldBubble.getRadius(), bubble.getRadius()));
        } else {
          for (Line line : newModel.getLinesOf(entity)) {
            animateuse.addAnimation(new FadeAnimation(line, true));
          }
        }
        oldModel.remove(entity);
      }
    }
    // oldModel_ now contains only disappearing bubbles
    for (final Entity entity : oldModel.getEntities()) {
      final Bubble oldBubble = oldModel.getBubble(entity);
      // fade out disappearing bubble
      animateuse.addAnimation(new ShapeAnimation.Zoom(oldBubble, oldBubble.getRadius(), 0));
      animateuse.addAnimation(new FadeAnimation(oldBubble, false));
    }
    for (final Line line : oldModel.removeLines(newModel)) {
      // fade out lines of disappearing bubbles
      animateuse.addAnimation(new FadeAnimation(line, false));
    }
  }

}
