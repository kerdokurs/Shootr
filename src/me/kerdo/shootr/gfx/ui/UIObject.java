package me.kerdo.shootr.gfx.ui;

import me.kerdo.shootr.Handler;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public abstract class UIObject {
  protected final Handler handler;

  protected int x, y;
  protected int width, height;
  protected Rectangle bounds;
  protected boolean visible = true, hovering = false;

  public UIObject(final Handler handler, final int x, final int y, final int width, final int height) {
    this.handler = handler;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;

    bounds = new Rectangle(x, y, width, height);
  }

  public abstract void tick(final double dt);

  public abstract void render(final Graphics g);

  public abstract void mousePressed(final MouseEvent e);

  public abstract void mouseReleased(final MouseEvent e);

  public abstract void mouseMoved(final MouseEvent e);

  public abstract void mouseWheelMoved(final MouseWheelEvent e);

  public abstract void mouseDragged(final MouseEvent e);

  public abstract void mouseClicked(final MouseEvent e);

  public abstract void mouseEntered(final MouseEvent e);

  public abstract void mouseExited(final MouseEvent e);

  public boolean isVisible() {
    return visible;
  }

  public void setVisible(final boolean visible) {
    this.visible = visible;

    if (visible == false)
      hovering = false;
  }

  public boolean isHovering() {
    return hovering;
  }

  public void setHovering(final boolean hovering) {
    this.hovering = hovering;
  }

  public Rectangle getBounds() {
    return bounds;
  }
}
