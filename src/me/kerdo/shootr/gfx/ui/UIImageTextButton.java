package me.kerdo.shootr.gfx.ui;

import me.kerdo.shootr.Handler;
import me.kerdo.shootr.gfx.Text;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UIImageTextButton extends UIImageButton {
  protected String text;
  protected final Color color;
  protected final Font font;

  public UIImageTextButton(final Handler handler, final int x, final int y, final int width, final int height, final UIClickable clickable, final BufferedImage[] frames, final String text, final Color color, final Font font) {
    super(handler, x, y, width, height, clickable, frames);
    this.text = text;
    this.color = color;
    this.font = font;
  }

  @Override
  public void render(final Graphics g) {
    super.render(g);
    Text.drawString(g, text, x + width / 2, y + height / 2, true, color, font);
  }

  public String getText() {
    return text;
  }

  public void setText(final String text) {
    this.text = text;
  }
}
