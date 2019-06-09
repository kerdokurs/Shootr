package me.kerdo.shootr.gfx.ui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class UIImageButton extends UIButton {
  private BufferedImage[] frames;

  public UIImageButton(final int x, final int y, final int width, final int height, final UIClickable clickable, final BufferedImage[] frames) {
    super(x, y, width, height, clickable);

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
}
