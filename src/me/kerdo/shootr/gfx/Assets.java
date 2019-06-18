package me.kerdo.shootr.gfx;

import me.kerdo.shootr.audio.Audio;

import javax.sound.sampled.Clip;
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
  public static BufferedImage controlPanelBackground;
  public static BufferedImage[] controlPanelTabBackground = new BufferedImage[3];

  public static BufferedImage weaponSlotBackground;

  public static BufferedImage characterSelectionBackground;

  public static BufferedImage[] buttonBackground = new BufferedImage[2];
  public static BufferedImage[] arrowRight, arrowLeft;

  public static BufferedImage heart, stamina;

  public static Clip gunshot;

  public static void init() {
    // Font loading
    andy8 = FontLoader.loadFont("AndyBold.ttf", 8);
    andy16 = FontLoader.loadFont("AndyBold.ttf", 16);
    andy24 = FontLoader.loadFont("AndyBold.ttf", 24);
    andy32 = FontLoader.loadFont("AndyBold.ttf", 32);
    andy64 = FontLoader.loadFont("AndyBold.ttf", 64);

    // Spritesheet loading
    spritesheets.put("default", new Spritesheet(ImageLoader.loadImage("/textures/spritesheet.png")));
    spritesheets.put("items", new Spritesheet(ImageLoader.loadImage("/textures/items.png")));
    spritesheets.put("weapons", new Spritesheet(ImageLoader.loadImage("/textures/weapons.png")));

    cursor = ImageLoader.loadImage("/textures/cursor.png");

    inventoryBackground = ImageLoader.loadImage("/textures/inventory/inventory_background.png");
    inventorySlotBackground = ImageLoader.loadImage("/textures/inventory/inventory_slot.png");
    itemDescriptionBackground = ImageLoader.loadImage("/textures/inventory/item_description_background.png");

    controlPanelBackground = ImageLoader.loadImage("/textures/control_panel_background.png");

    {
      final Spritesheet controlPanelTabBackgroundSheet = new Spritesheet(ImageLoader.loadImage("/textures/panel/tab_background.png"));

      for (int i = 0; i < 3; i++)
        controlPanelTabBackground[i] = controlPanelTabBackgroundSheet.crop(i, 0, 1, 1);
    }

    weaponSlotBackground = ImageLoader.loadImage("/textures/inventory/weapon_slot_background.png");

    buttonBackground[0] = ImageLoader.loadImage("/textures/menu/button_background.png");
    buttonBackground[1] = ImageLoader.loadImage("/textures/menu/button_background_hover.png");

    characterSelectionBackground = ImageLoader.loadImage("/textures/char_sel_background.png");

    heart = ImageLoader.loadImage("/textures/heart.png");
    stamina = ImageLoader.loadImage("/textures/stamina.png");

    {
      final Spritesheet arrowRightSpritesheet = new Spritesheet(ImageLoader.loadImage("/textures/arrow_right.png"));
      BufferedImage sheet = arrowRightSpritesheet.getSheet();
      int amt = sheet.getWidth() / 16;

      arrowRight = new BufferedImage[amt];

      for (int i = 0; i < amt; i++)
        arrowRight[i] = arrowRightSpritesheet.crop16(i, 0);

      Spritesheet arrowLeftSpritesheet = new Spritesheet(ImageLoader.loadImage("/textures/arrow_left.png"));
      sheet = arrowLeftSpritesheet.getSheet();
      amt = sheet.getWidth() / 16;

      arrowLeft = new BufferedImage[amt];

      for (int i = 0; i < amt; i++)
        arrowLeft[i] = arrowLeftSpritesheet.crop16(i, 0);
    }

    gunshot = Audio.loadSound("gunshot.wav");
  }

  public static BufferedImage get(final String name) {
    return assets.get(name) != null ? assets.get(name) : assets.get("default");
  }
}
