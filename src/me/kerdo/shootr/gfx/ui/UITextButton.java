package me.kerdo.shootr.gfx.ui;

import me.kerdo.shootr.Handler;
import me.kerdo.shootr.gfx.Text;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class UITextButton extends UIButton {
  private String text;
  private Color[] colors;
  private final Font font;

  // TODO: Use dynamic sizing according to text.
  public UITextButton(final Handler handler, final int x, final int y, final int width, final int height, final UIClickable clickable, final String text, final Color[] colors, final Font font) {
    super(handler, x, y, width, height, clickable);

    this.text = text;
    this.colors = colors;
    this.font = font;
  }

  @Override
  public void tick(final double dt) {

  }

  @Override
  public void render(final Graphics g) {
    Text.drawString(g, text, (x - width / 2), (y - height / 2), true, colors[hovering ? 1 : 0], font);
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

  public String getText() {
    return text;
  }

  public void setText(final String text) {
    this.text = text;
  }

  public Color[] getColors() {
    return colors;
  }

  public void setColors(final Color[] colors) {
    this.colors = colors;
  }

  public Font getFont() {
    return font;
  }
}
