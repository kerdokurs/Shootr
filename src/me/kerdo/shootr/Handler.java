package me.kerdo.shootr;

import me.kerdo.shootr.gfx.Camera;
import me.kerdo.shootr.input.KeyManager;
import me.kerdo.shootr.input.MouseManager;
import me.kerdo.shootr.world.World;

public class Handler {
  private final Game game;

  private World world;

  public Handler(final Game game) {
    this.game = game;
  }

  public Game getGame() {
    return game;
  }

  public Camera getCamera() {
    return game.getCamera();
  }

  public World getWorld() {
    return world;
  }

  public void setWorld(final World world) {
    this.world = world;
  }

  public int getWidth() {
    return game.getWidth();
  }

  public int getHeight() {
    return game.getHeight();
  }

  public KeyManager getKeyManager() {
    return game.getKeyManager();
  }

  public MouseManager getMouseManager() {
    return game.getMouseManager();
  }
}
