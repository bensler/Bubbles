package com.bensler.bubbles.client;

import java.util.LinkedList;

public class History {

  public final static int MAX_HISTORY_SIZE = 100;
  
  private final LinkedList<Entity> past_;
  private Entity current_;
  private final LinkedList<Entity> future_;

  public History(Entity currentEntity) {
    super();
    past_ = new LinkedList<Entity>();
    current_ = currentEntity;
    future_ = new LinkedList<Entity>();
  }
  
  public void addStep(Entity entity) {
    if ((entity != null) && (entity != current_)) {
      past_.addLast(current_);
      current_ = entity;
      future_.clear();
      while (past_.size() > MAX_HISTORY_SIZE) {
        past_.removeFirst();
      }
    }
  }
  
  public Entity stepBack() {
    final Entity oldCurrent = current_;
    
    if (past_.size() > 0) {
      future_.addFirst(current_);
      current_ = past_.removeLast();
    }
    return ((oldCurrent == current_) ? null : current_);
  }
  
  public Entity stepForward() {
    final Entity oldCurrent = current_;
    
    if (future_.size() > 0) {
      past_.addLast(current_);
      current_ = future_.removeFirst();
    }
    return ((oldCurrent == current_) ? null : current_);
  }
  
}
