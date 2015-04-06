package com.bensler.bubbles.client;

public class RelativePositionImpl extends Object implements RelativePosition {
  
  public static RelativePositionImpl minus(RelativePosition pos1, RelativePositionImpl pos2) {
    return new RelativePositionImpl(
      pos1.getDx() - pos2.getDx(), 
      pos1.getDy() - pos2.getDy(),
      pos2.getRelatedEntity()
    );
  }
  
  private final int dx_;
  private final int dy_;

  private final EntityImpl relatedEntity_;
        
  public RelativePositionImpl(int dx, int dy, EntityImpl relatedEntity) {
    dx_ = dx;
    dy_ = dy;
    relatedEntity_ = relatedEntity;
  }

  @Override
  public Integer getId() {
    int i = 0;
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int getDx() {
    return dx_;
  }

  @Override
  public int getDy() {
    return dy_;
  }

  @Override
  public EntityImpl getRelatedEntity() {
    return relatedEntity_;
  }

  @Override
  public String toString() {
    return "rel(" + dx_ + "," + dy_ + ")";
  }

  public int distance(RelativePosition other) {
    final int xDiff = (dx_ - other.getDx());
    final int yDiff = (dy_ - other.getDy());

    return (int)Math.round(Math.sqrt((xDiff * xDiff) + (yDiff * yDiff)));
  }
  
}