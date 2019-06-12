package me.kerdo.shootr.gfx;

import java.awt.*;

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

      /*if (text.equalsIgnoreCase("multiplayer")) {
        System.out.println(fm.stringWidth(text));
        System.out.println(fm.getHeight());

        System.exit(1);
      }*/
    }

    g.drawString(text, x, y);
  }
}