package com.bensler.bubbles.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;

class Controller extends Object implements MouseDownHandler, MouseUpHandler, MouseMoveHandler, KeyDownHandler {

  private final BubblesDisplay grid_;
  private final Canvas canvas_;
  private final History history_;

  private DragState dragState_;
  
  Controller(BubblesDisplay grid) {
    grid_ = grid;
    canvas_ = grid_.getCanvas();
    history_ = new History(grid_.getModel().getCenterEntity());
    canvas_.addMouseDownHandler(this);
    canvas_.addMouseUpHandler(this);
    canvas_.addMouseMoveHandler(this);
    canvas_.addKeyDownHandler(this);
  }

  @Override
  public void onKeyDown(KeyDownEvent event) {
    if (event.isAltKeyDown()) {
      Entity targetEntity = null;
      
      if (event.isLeftArrow()) {
        targetEntity = history_.stepBack();
        event.preventDefault();
      }
      if (event.isRightArrow()) {
        targetEntity = history_.stepForward();
        event.preventDefault();
      }
      
      if (targetEntity != null) {
        grid_.finishAnimation();
        grid_.focusBubble(targetEntity);
      }
    }
    if (event.getNativeKeyCode() == KeyCodes.KEY_ESCAPE) {
      if (dragState_ != null) {
        grid_.moveBubble(dragState_.bubble_, dragState_.oldPosition_);
        dragState_ = null;
      }
    }
  }

  @Override
  public void onMouseDown(MouseDownEvent event) {
    final int x = event.getX();
    final int y = event.getY();
    final Bubble bubble = grid_.getModel().hit(x, y);
    
    dragState_ = null;
    if ((bubble != null) && (bubble != grid_.getModel().getCenterBubble())) {
      dragState_ = new DragState(bubble, grid_.createRelativePosition(x, y, bubble.getEntity()));
      event.preventDefault();
    }
  }
  
  @Override
  public void onMouseMove(final MouseMoveEvent event) {
    if (dragState_ != null) {
      final DragState dragState = dragState_;
      final int x = event.getX();
      final int y = event.getY();

      Scheduler.get().scheduleDeferred(new ScheduledCommand() {
        @Override
        public void execute() {
          moveBubbleOnScreen(dragState, x, y);
          dragState.moved();
        }
      });
      event.preventDefault();
    }
  }
  
  private void moveBubbleOnScreen(DragState dragState, int x, int y) {
    final Bubble bubble = dragState.getBubble();

    grid_.moveBubble(bubble, dragState.adjustPosition(
      grid_.createRelativePosition(x, y, bubble.getEntity())
    ));
  }
  
  @Override
  public void onMouseUp(MouseUpEvent event) {
    if (dragState_ != null) {
      final Bubble hitBubble = dragState_.getBubble();

      if (dragState_.hasMoved()) {
        moveBubbleOnScreen(dragState_, event.getX(), event.getY());
        grid_.savePosition(hitBubble);
      } else {
        final Entity newEntity;
        
        grid_.moveBubble(hitBubble, dragState_.oldPosition_);
        newEntity = grid_.focusBubble(hitBubble.getEntity());
        if (newEntity != null) {
          history_.addStep(newEntity);
        }
      }
      dragState_ = null;
      event.preventDefault();
    }
  }
  
  static class DragState extends Object {

    private final RelativePositionImpl oldPosition_;
    final Bubble bubble_;
    final RelativePositionImpl dPosition_;
    
    private boolean moved_;

    DragState(Bubble bubble, RelativePositionImpl clickPosition) {
      final RelativePositionImpl bubblePosition = bubble.getRelativePosition();
      
      bubble_ = bubble;
      dPosition_ = RelativePositionImpl.minus(clickPosition, bubblePosition);
      oldPosition_ = new RelativePositionImpl(
        bubblePosition.getDx(), bubblePosition.getDy(), bubble.getEntity()
      );
      moved_ = false;
    }

    RelativePositionImpl adjustPosition(RelativePosition position) {
      return RelativePositionImpl.minus(position, dPosition_);
    }

    boolean hasMoved() {
      return moved_;
    }

    Bubble getBubble() {
      return bubble_;
    }

    void moved() {
      moved_ |= (oldPosition_.distance(bubble_.getRelativePosition()) > bubble_.getRadius());
    }
    
  }
  
}