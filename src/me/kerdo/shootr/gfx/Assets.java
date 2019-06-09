package me.kerdo.shootr.gfx;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Assets {
  public static final int WIDTH = 32;
  public static final int HEIGHT = 32;

  public static Font andy8, andy16, andy32, andy64;

  public static Spritesheet spritesheet;
  public static Map<String, BufferedImage> assets = new HashMap<>();

  public static void init() {
    andy8 = FontLoader.loadFont("AndyBold.ttf", 8);
    andy16 = FontLoader.loadFont("AndyBold.ttf", 16);
    andy32 = FontLoader.loadFont("AndyBold.ttf", 32);
    andy64 = FontLoader.loadFont("AndyBold.ttf", 64);

    spritesheet = new Spritesheet(ImageLoader.loadImage("/textures/spritesheet.png"));

    assets.put("error", spritesheet.crop32(3, 1));
  }

  public static BufferedImage get(final String name) {
    return assets.get(name) != null ? assets.get(name) : assets.get("error");
  }
}
