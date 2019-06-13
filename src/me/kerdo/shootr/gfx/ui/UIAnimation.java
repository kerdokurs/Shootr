package me.kerdo.shootr.gfx.ui;

import me.kerdo.shootr.Handler;
import me.kerdo.shootr.gfx.Animation;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class UIAnimation extends UIObject {
  private Animation animation;
  private final boolean center;

  public UIAnimation(final Handler handler, final int x, final int y, final int width, final int height, final Animation animation, final boolean center) {
    super(handler, x, y, width, height);
    this.animation = animation;
    this.center = center;
  }

  @Override
  public void tick(final double dt) {
    animation.tick(dt);
  }

  @Override
  public void render(final Graphics g) {
    g.drawImage(animation.getCurrentFrame(), x - (center ? width / 2 : 0), y - (center ? height / 2 : 0), width, height, null);
  }

  @Override
  public void mousePressed(final MouseEvent e) {
  }

  @Override
  public void mouseReleased(final MouseEvent e) {
  }

  @Override
  public void mouseMoved(final MouseEvent e) {
  }

  @Override
  public void mouseWheelMoved(final MouseWheelEvent e) {
  }

  @Override
  public void mouseDragged(final MouseEvent e) {
  }

  @Override
  public void mouseClicked(final MouseEvent e) {
  }

  @Override
  public void mouseEntered(final MouseEvent e) {
  }

  @Override
  public void mouseExited(final MouseEvent e) {
  }
}
