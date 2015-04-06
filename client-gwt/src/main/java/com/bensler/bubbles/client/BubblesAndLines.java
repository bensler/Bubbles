package com.bensler.bubbles.client;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/** A {@link DisplayMap} blown up to a bunch of {@link Bubble}s and {@link Line}s. */
class BubblesAndLines extends Object {
  
  private final BubblesDisplay display_;
  private final LinkedHashMap<Entity, Bubble> bubbles_;
  private final ArrayList<Line> lines_;
  
  private Bubble centerBubble_;
  private DisplayMap displayMap_; 
  
  public BubblesAndLines(BubblesDisplay display) {
    display_ = display;
    bubbles_ = new LinkedHashMap<Entity, Bubble>();
    lines_ = new ArrayList<Line>();
  }
  
  public void displayMapReceived(DisplayMap displayMap, int width, int height) {
    final EntityImpl centerEntity = displayMap.getEntity();
    final int radius = 10;
    final int maxDx = (width / 2) - radius; 
    final int maxDy = (height / 2) - radius; 
    
    displayMap_ = new DisplayMap(centerEntity);
    displayMap_.putPosition(new RelativePositionImpl(0, 0, centerEntity));
    centerBubble_ = new Bubble(centerEntity, this, 20);
    bubbles_.put(centerEntity, centerBubble_);
    for (EntityImpl entity : displayMap.getRelatedEntities()) {
      final Bubble bubble = new Bubble(entity, this, radius);
      final RelativePositionImpl position = displayMap.getPosition(entity);
      
      bubbles_.put(entity, bubble);
      lines_.add(new Line(centerBubble_, bubble));
      displayMap_.putPosition(new RelativePositionImpl(
        Math.max(-maxDx, Math.min(position.getDx(), maxDx)),
        Math.max(-maxDy, Math.min(position.getDy(), maxDy)),
        entity
      ));
    }
  }
  
  public EntityImpl getCenterEntity() {
    return ((centerBubble_ != null) ? centerBubble_.getEntity() : null);
  }
  
  Bubble getCenterBubble() {
    return centerBubble_;
  }
  
  void draw() {
    for (Line line : lines_) {
      line.draw(display_.getCanvas());
    }
    for (Bubble bubble : bubbles_.values()) {
      bubble.draw(display_.getCanvas());
    }
  }

  Bubble hit(int x, int y) {
    for (Bubble bubble : bubbles_.values()) {
      if (bubble.hit(x, y)) {
        return bubble;
      }
    }
    return null;
  }

  public ArrayList<Line> getLines(Entity entity) {
    final ArrayList<Line> lines = new ArrayList<Line>();
    final Bubble bubble = bubbles_.get(entity);
    
    for (Line line : lines_) {
      if (line.connectsTo(bubble)) {
        lines.add(line);
      }
    }
    return lines; 
  }

  public Bubble getBubble(Entity entity) {
    return bubbles_.get(entity);
  }

  public Iterable<Entity> getEntities() {
    return bubbles_.keySet();
  }

  private Bubble getBubble(Entity entity, BubblesAndLines fallbackModel) {
    return (bubbles_.containsKey(entity) ? getBubble(entity) : fallbackModel.getBubble(entity));
  }
  
  /** Recreates all lines resolving begin and end bubble against our bubbles
   * and if needed against the other models bubbles.
   * 
   * @return all new lines */
  public ArrayList<Line> removeLines(BubblesAndLines newModel) {
    final ArrayList<Line> lines = new ArrayList<Line>();
    
    lines_.removeAll(newModel.lines_);
    for (Line line : lines_) {
      lines.add(new Line(
        getBubble(line.getFrom().getEntity(), newModel), 
        getBubble(line.getTo().getEntity(), newModel)
      ));
    }
    lines_.clear();
    lines_.addAll(lines);
    return lines;
  }
  
  void clear() {
    centerBubble_ = null;
    bubbles_.clear();
    lines_.clear();
  }
  
  public void remove(Entity entity) {
    final Bubble removedBubble = bubbles_.remove(entity);

    if (centerBubble_ == removedBubble) {
      centerBubble_ = null;
    }
  }

  public ArrayList<Line> getLinesOf(Entity entity) {
    final Bubble bubble = getBubble(entity);
    final ArrayList<Line> lines = new ArrayList<Line>();
    
    for (Line line : lines_) {
      if (line.connectsTo(bubble)) {
        lines.add(line);
      }
    }
    return lines;
  }

  public RelativePositionImpl getRelativePosition(Bubble bubble) {
    return displayMap_.getPosition(bubble.getEntity());
  }

  public int getCenterX() {
    return display_.getCenterX();
  }
  
  public int getCenterY() {
    return display_.getCenterY();
  }

  public void moveTo(Bubble bubble, RelativePositionImpl position) {
    displayMap_.setPosition(bubble.getEntity(), position);
  }
  
}
