package me.kerdo.shootr.item;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.kerdo.shootr.Handler;
import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.gfx.Spritesheet;
import me.kerdo.shootr.utils.Utils;

import java.awt.image.BufferedImage;

public class Item {
  public static Item[] ITEMS = new Item[256];

  public static int WIDTH = 32, HEIGHT = 32;

  private final Handler handler;

  private int id;
  private String name;
  private String description;
  private String tooltip;
  private BufferedImage[] textures;

  public Item(final Handler handler, final int id, final String name, final String description, final String tooltip, final BufferedImage[] textures) {
    this.handler = handler;
    this.id = id;
    this.name = name;
    this.description = description;
    this.tooltip = tooltip;
    this.textures = textures;

    ITEMS[id] = this;
  }

  public static void init(final Handler handler) {
    final String data = Utils.loadFileFromJAR("/data/items.json");

    final JsonParser parser = new JsonParser();
    final JsonElement root = parser.parse(data);
    final JsonArray items = root.getAsJsonArray();

    for (final JsonElement item : items) {
      final JsonObject obj = item.getAsJsonObject();

      final int id = obj.get("id").getAsInt();
      final String name = obj.get("name").getAsString();
      final String description = obj.get("description").getAsString();
      final String tooltip = obj.get("tooltip").getAsString();

      final JsonObject spritesheetData = obj.get("spritesheet").getAsJsonObject();
      final String spritesheetName = spritesheetData.get("name").getAsString();

      final JsonArray textCoords = spritesheetData.get("coords").getAsJsonArray();
      final JsonArray notHoverCoords = textCoords.get(0).getAsJsonArray(),
              hoverCoords = textCoords.get(1).getAsJsonArray();

      final int textX = notHoverCoords.get(0).getAsInt(),
              textY = notHoverCoords.get(1).getAsInt();
      final int textHX = hoverCoords.get(0).getAsInt(),
              textHY = hoverCoords.get(1).getAsInt();

      final JsonArray size = spritesheetData.get("size").getAsJsonArray();
      final int width = size.get(0).getAsInt(),
              height = size.get(1).getAsInt();

      final Spritesheet spritesheet = Assets.spritesheets.get(spritesheetName);
      final BufferedImage texture = spritesheet.crop(textX * width, textY * height, width, height);
      final BufferedImage textureH = spritesheet.crop(textHX * width, textHY * height, width, height);

      new Item(handler, id, name, description, tooltip, new BufferedImage[] { texture, textureH });
    }
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getTooltip() {
    return tooltip;
  }

  public void setTooltip(final String tooltip) {
    this.tooltip = tooltip;
  }

  public BufferedImage[] getTextures() {
    return textures;
  }

  public void setTextures(final BufferedImage[] textures) {
    this.textures = textures;
  }

  public BufferedImage getTexture(final boolean hovering) {
    return textures[hovering ? 1 : 0];
  }
}
