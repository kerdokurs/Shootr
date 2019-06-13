package me.kerdo.shootr.gfx.ui;

import me.kerdo.shootr.Handler;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;

public class UIImage extends UIObject {
  protected final BufferedImage texture;
  protected final boolean center;

  public UIImage(final Handler handler, final int x, final int y, final int width, final int height, final BufferedImage texture, final boolean center) {
    super(handler, x, y, width, height);
    this.texture = texture;
    this.center = center;
  }

  @Override
  public void tick(final double dt) {
  }

  @Override
  public void render(final Graphics g) {
    g.drawImage(texture, x - (center ? width / 2 : 0), y - (center ? height / 2 : 0), width, height, null);
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
