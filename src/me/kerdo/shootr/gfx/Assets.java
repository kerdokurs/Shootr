package me.kerdo.shootr.gfx;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Assets {
  public static final int WIDTH = 32;
  public static final int HEIGHT = 32;

  public static Font andy8, andy16, andy24, andy32, andy64;

  public static Map<String, Spritesheet> spritesheets = new HashMap<>();
  public static Map<String, BufferedImage> assets = new HashMap<>();
  public static BufferedImage cursor;

  public static BufferedImage inventoryBackground,
          inventorySlotBackground, itemDescriptionBackground;

  public static void init() {
    andy8 = FontLoader.loadFont("AndyBold.ttf", 8);
    andy16 = FontLoader.loadFont("AndyBold.ttf", 16);
    andy24 = FontLoader.loadFont("AndyBold.ttf", 24);
    andy32 = FontLoader.loadFont("AndyBold.ttf", 32);
    andy64 = FontLoader.loadFont("AndyBold.ttf", 64);

    spritesheets.put("default", new Spritesheet(ImageLoader.loadImage("/textures/spritesheet.png")));
    spritesheets.put("items", new Spritesheet(ImageLoader.loadImage("/textures/items.png")));

    cursor = ImageLoader.loadImage("/textures/cursor.png");

    inventoryBackground = ImageLoader.loadImage("/textures/inventory/inventory_background.png");
    inventorySlotBackground = ImageLoader.loadImage("/textures/inventory/inventory_slot.png");
    itemDescriptionBackground = ImageLoader.loadImage("/textures/inventory/item_description_background.png");
  }

  public static BufferedImage get(final String name) {
    return assets.get(name) != null ? assets.get(name) : assets.get("error");
  }
}
