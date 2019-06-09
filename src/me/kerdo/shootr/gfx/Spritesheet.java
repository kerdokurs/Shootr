package me.kerdo.shootr.gfx;

import java.awt.image.BufferedImage;

public class Spritesheet {
  private BufferedImage sheet;

  public Spritesheet(final BufferedImage sheet) {
    this.sheet = sheet;
  }

  public BufferedImage crop(final int x, final int y, final int width, final int height) {
    return sheet.getSubimage(x, y, width, height);
  }

  public BufferedImage crop64(final int x, final int y) {
    return sheet.getSubimage(x * 64, y * 64, 64, 64);
  }

  public BufferedImage crop32(final int x, final int y) {
    return sheet.getSubimage(x * 32, y * 32, 32, 32);
  }

  public BufferedImage crop16(final int x, final int y) {
    return sheet.getSubimage(x * 16, y * 16, 16, 16);
  }
}