package me.kerdo.shootr.gfx.ui;

import me.kerdo.shootr.Handler;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;

public class UIImageButton extends UIButton {
  protected BufferedImage[] frames;
  protected String tooltip;

  public UIImageButton(final Handler handler, final int x, final int y, final int width, final int height, final UIClickable clickable, final BufferedImage[] frames, final String tooltip) {
    super(handler, x, y, width, height, clickable);
    this.tooltip = tooltip;

    this.frames = frames;
  }

  @Override
  public void tick(final double dt) {
  }

  @Override
  public void render(final Graphics g) {
    g.drawImage(frames[hovering ? 1 : 0], x, y, width, height, null);
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
  public void mouseEntered(final MouseEvent e) {
  }

  @Override
  public void mouseExited(final MouseEvent e) {
  }

  public BufferedImage[] getFrames() {
    return frames;
  }

  public void setFrames(final BufferedImage[] frames) {
    this.frames = frames;
  }

  public String getTooltip() {
    return tooltip;
  }
}
