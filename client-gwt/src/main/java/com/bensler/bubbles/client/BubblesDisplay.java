package com.bensler.bubbles.client;

import com.bensler.bubbles.client.anim.Animateuse;
import com.bensler.bubbles.client.anim.FlashAnimation;
import com.bensler.bubbles.client.net.NetDataSource.DisplayMapCallback;
import com.google.gwt.canvas.client.Canvas;

/** Visual area where {@link Entity}s and their {@link Relationship} are displayed
 * using a {@link BubblesAndLines} model created out of a {@link DisplayMap}. */
public class BubblesDisplay extends Object implements DisplayMapCallback {

  public final static CssColorWithAlpha COLOR_BUBBLE_BORDER = new CssColorWithAlpha("#151515");
  public final static CssColorWithAlpha COLOR_BUBBLE_FILL = new CssColorWithAlpha("#4976f3");
  public final static CssColorWithAlpha COLOR_GRID = new CssColorWithAlpha("#888888");
  
  private final DataProvider dataProvider_;
  private final Animateuse animateuse_;
  private final Grid grid_;
  private final Canvas canvas_;
  private final DescriptionPanel descriptionPanel_;
  
  private BubblesAndLines model_;
  private BubblesAndLines oldModel_;
  
  private int width_;
  private int height_;
  
  BubblesDisplay(Canvas canvas, DataProvider dataProvider, DescriptionPanel descriptionPanel) {
    super();
    dataProvider_ = dataProvider;
    animateuse_ = new Animateuse(this, 1000);
    
    canvas_ = canvas;
    grid_ = new Grid();

    model_ = new BubblesAndLines(this);
    oldModel_ = new BubblesAndLines(this);
    dataProvider_.getDisplayMap(null, this);
    new Controller(this);
    descriptionPanel_ = descriptionPanel;
  }

  public void draw() {
    grid_.draw(canvas_, model_.getCenterBubble(), width_, height_);
    if (oldModel_ != null) { 
      oldModel_.draw();
    }
    model_.draw();
  }

  public Canvas getCanvas() {
    return canvas_;
  }

  public BubblesAndLines getModel() {
    return model_;
  }
  
  public Entity focusBubble(Entity hitEntity) {
    if (!animateuse_.isAnimating()) {
      if (
        (hitEntity != model_.getCenterEntity()) 
        && (model_.getBubble(hitEntity) != null)
      ) {
        setCurrentEntity(hitEntity);
        return hitEntity;
      }
    }
    return null;
  }

  private void setCurrentEntity(Entity hitEntity) {
    animateuse_.clearAnimations();
    oldModel_ = model_;
    model_ = new BubblesAndLines(this);
    dataProvider_.getDisplayMap(hitEntity, this);
    descriptionPanel_.showEntity(hitEntity);
  }
  
  @Override
  public void displayMapReceived(DisplayMap displayMap) {
    model_.displayMapReceived(displayMap, width_, height_);
    new AnimationCreator().createAnimation(model_, oldModel_, animateuse_);
    animateuse_.addAnimation(new FlashAnimation(grid_));
    animateuse_.start();
  }

  public void animationFinished() {
    oldModel_.clear();
    draw();
  }

  public void savePosition(Bubble bubble) {
    final RelativePositionImpl position = model_.getRelativePosition(bubble);
    
    if (position != null) {
      dataProvider_.saveRelativePosition(
        model_.getCenterEntity(), bubble.getEntity(), position
      );
    }
  }

  public void focus() {
    canvas_.setFocus(true);
  }

  public void finishAnimation() {
    animateuse_.cancel();
  }

  public void setSize(int width, int height) {
    width_ = width;
    height_ = height;
    canvas_.setWidth(Integer.toString(width));
    canvas_.setHeight(Integer.toString(height));
    draw();
  }

  public int getCenterX() {
    return width_ / 2;
  }

  public int getCenterY() {
    return height_ / 2;
  }

  public void moveBubble(Bubble bubble, RelativePositionImpl position) {
    model_.moveTo(bubble, position);
    draw();
  }

  public RelativePositionImpl createRelativePosition(int x, int y, EntityImpl entity) {
    return new RelativePositionImpl(x - getCenterX(), y - getCenterY(), entity);
  }

}