package me.kerdo.shootr.world;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {
  public static Tile[] TILES = new Tile[256];

  private final String name;
  private final int id;
  private final BufferedImage texture;
  private final boolean solid;
  private final Rectangle bounds;

  public static final int TILE_WIDTH = 64, TILE_HEIGHT = 64;

  public Tile(final String name, int id, final BufferedImage texture, final boolean solid, final Rectangle bounds) {
    this.name = name;
    this.id = id;
    this.texture = texture;
    this.solid = solid;
    this.bounds = bounds;

    TILES[id] = this;
  }

  public void tick(final double dt) {
  }

  public void render(final Graphics g, final int x, final int y) {
    g.drawImage(texture, x, y, TILE_WIDTH, TILE_HEIGHT, null);

    // Add different sized hit boxes

    /*g.setColor(Color.WHITE);
    g.drawRect(x + bounds.x, y + bounds.y, bounds.width, bounds.height);*/
  }

  public static void init() {
    final String data = Utils.loadFileFromJAR("/data/tiles.json");

    final JsonParser parser = new JsonParser();
    final JsonElement root = parser.parse(data);
    final JsonArray tiles = root.getAsJsonArray();

    tiles.forEach((tile) -> {
      final JsonObject obj = tile.getAsJsonObject();

      final String name = obj.get("name").getAsString();
      final int id = obj.get("id").getAsInt();
      final JsonArray textCoords = obj.get("texture").getAsJsonArray();
      final boolean solid = obj.get("solid").getAsBoolean();

      final int textX = textCoords.get(0).getAsInt(),
              textY = textCoords.get(1).getAsInt();

      final JsonArray boundsObj = obj.get("bounds").getAsJsonArray();
      final Rectangle bounds = new Rectangle(boundsObj.get(0).getAsInt(), boundsObj.get(1).getAsInt(), TILE_WIDTH - 2 * boundsObj.get(2).getAsInt(), TILE_HEIGHT - 2 * boundsObj.get(3).getAsInt());


      new Tile(name, id, Assets.spritesheets.get("default").crop32(textX, textY), solid, bounds);
    });
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  public boolean isSolid() {
    return solid;
  }

  public Rectangle getBounds() {
    return bounds;
  }
}
