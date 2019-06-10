package me.kerdo.shootr.world;

import me.kerdo.shootr.Handler;
import me.kerdo.shootr.entity.EntityManager;
import me.kerdo.shootr.entity.creature.Player;
import me.kerdo.shootr.utils.Utils;

import java.awt.*;

public class World {
  private final Handler handler;

  private EntityManager entityManager;
  private Player player;

  private int width, height;

  private int[][] tiles;

  public World(final Handler handler) {
    this.handler = handler;

    entityManager = new EntityManager(handler);

    load("world1");
  }

  public void tick(final double dt) {
  entityManager.tick(dt);
  }

  public void render(final Graphics g) {
    final int xStart = (int) Math.max(0, handler.getCamera().getxOff() / Tile.TILE_WIDTH);
    final int xEnd = (int) Math.min(width, (handler.getCamera().getxOff() + handler.getWidth()) / Tile.TILE_WIDTH + 1);
    final int yStart = (int) Math.max(0, handler.getCamera().getyOff() / Tile.TILE_HEIGHT);
    final int yEnd = (int) Math.min(height, (handler.getCamera().getyOff() + handler.getHeight()) / Tile.TILE_WIDTH + 1);

    for (int x = xStart; x < xEnd; x++) {
      for (int y = yStart; y < yEnd; y++) {
        getTile(x, y).render(g, (int) (x * Tile.TILE_WIDTH - handler.getCamera().getxOff()),
                (int) (y * Tile.TILE_HEIGHT - handler.getCamera().getyOff()));
      }
    }

    entityManager.render(g);
  }

  public Tile getTile(final int x, final int y) {
    if (x < 0 || x >= width || y < 0 || y >= height)
      return Tile.TILES[0];

    final Tile t = Tile.TILES[tiles[x][y]];

    if (t == null)
      return Tile.TILES[0];

    return t;
  }

  private void load(final String path) {
    final String data = Utils.loadFileFromJAR("/worlds/" + path + ".txt");

    final String[] tokens = data.split("\\s+");

    width = Integer.parseInt(tokens[0]);
    height = Integer.parseInt(tokens[1]);

    tiles = new int[width][height];

    for (int y = 0; y < height; y++)
      for (int x = 0; x < width; x++)
        tiles[x][y] = Integer.parseInt(tokens[(x + y * height) + 2]);
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public EntityManager getEntityManager() {
    return entityManager;
  }

  public Player getPlayer() {
    return player;
  }

  public void setPlayer(final Player player) {
    this.player = player;
  }
}
