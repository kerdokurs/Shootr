package me.kerdo.shootr.gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class Text {
  public static void drawString(final Graphics g, final String text, final int xPos, final int yPos, final boolean center, final Color color, final Font font) {
    g.setColor(color);
    g.setFont(font);

    int x = xPos;
    int y = yPos;

    if (center) {
      final FontMetrics fm = g.getFontMetrics(font);
      x = xPos - fm.stringWidth(text) / 2;
      y = (yPos - fm.getHeight() / 2) + fm.getAscent();
    }

    g.drawString(text, x, y);
  }
}