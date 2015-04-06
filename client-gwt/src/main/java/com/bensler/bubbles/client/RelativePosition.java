package com.bensler.bubbles.client;


public interface RelativePosition {

  public Integer getId();

  public int getDx();
  public int getDy();

  public Entity getRelatedEntity();

}