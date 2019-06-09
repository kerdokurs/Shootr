package me.kerdo.shootr.entity.immovable;

import me.kerdo.shootr.Handler;
import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.world.Tile;

import java.awt.*;

public class Tree extends ImmovableEntity {
  public Tree(final Handler handler, final float x, final float y) {
    super(handler, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT * 2, 250);

    bounds.x = 10;
    bounds.y = (int) (height / 1.5);
    bounds.width = width - 20;
    bounds.height = (int) (height - height / 1.5);
  }

  @Override
  public void tick(double dt) {

  }

  @Override
  public void render(Graphics g) {
    g.drawImage(Assets.get("tree"), (int) (x - handler.getCamera().getxOff()), (int) (y - handler.getCamera().getyOff()), width, height, null);
  }

  @Override
  public void die() {
  }
}
