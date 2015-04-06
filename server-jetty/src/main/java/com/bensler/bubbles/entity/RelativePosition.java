package com.bensler.bubbles.entity;


public class RelativePosition {

  private final Long id_;

  private final Entity releatedEntity_;

  private final int dx_;
  private final int dy_;

  RelativePosition() {
    this(0, 0, null);
  }

  public RelativePosition(int dx, int dy, Entity relatedEntity) {
    super();
    id_ = null;
    dx_ = dx;
    dy_ = dy;
    releatedEntity_ = relatedEntity;
  }

  public Long getId() {
    return id_;
  }

  public Entity getRelatedEntity() {
    return releatedEntity_;
  }

  public int getDx() {
    return dx_;
  }

  public int getDy() {
    return dy_;
  }

  @Override
  public String toString() {
    return "=>(" + dx_ + "," + dy_ + ")" + releatedEntity_;
  }

}
