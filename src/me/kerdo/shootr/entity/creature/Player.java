package me.kerdo.shootr.entity.creature;

import me.kerdo.shootr.Handler;
import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.gfx.Text;
import me.kerdo.shootr.world.Tile;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends Creature {
  public Player(final Handler handler, final float x, final float y) {
    super(handler, x, y, Creature.DEFAULT_WIDTH, Creature.DEFAULT_HEIGHT, 100);
  }

  public void getInput() {
    xMove = 0;
    yMove = 0;

    if (handler.getKeyManager().isKeyPressed(KeyEvent.VK_UP))
      yMove = -speed;
    if (handler.getKeyManager().isKeyPressed(KeyEvent.VK_DOWN))
      yMove = +speed;
    if (handler.getKeyManager().isKeyPressed(KeyEvent.VK_LEFT))
      xMove = -speed;
    if (handler.getKeyManager().isKeyPressed(KeyEvent.VK_RIGHT))
      xMove = speed;
  }

  @Override
  public void tick(final double dt) {
    getInput();
    move();
    handler.getCamera().centerOnEntity(this);
  }

  @Override
  public void render(final Graphics g) {
    g.drawImage(Assets.get("player"), (int) (x - handler.getCamera().getxOff()), (int) (y - handler.getCamera().getyOff()), width, height, null);
    g.setColor(Color.WHITE);
    g.drawLine((int) (x - handler.getCamera().getxOff() + width / 2),
            (int) (y - handler.getCamera().getyOff() + height / 2), handler.getGame().getMouseManager().getMouseX(),
            handler.getGame().getMouseManager().getMouseY());

    Text.drawString(g, "Block: " + handler.getWorld().getTile(
            (int) ((handler.getMouseManager().getMouseX() + handler.getCamera().getxOff()) / Tile.TILE_WIDTH),
            (int) ((handler.getMouseManager().getMouseY() + handler.getCamera().getyOff())) / Tile.TILE_WIDTH)
                                           .getName(), 15, 50, false, Color.WHITE, Assets.andy16);

    Text.drawString(g, "Mouse Position: " + handler.getMouseManager().getMouseX() + ":"
                               + handler.getMouseManager().getMouseY(), 15, 70, false, Color.WHITE, Assets.andy16);
    Text.drawString(g, "Player Position: " + x + ":" + y, 15, 90, false, Color.WHITE, Assets.andy16);
  }

  public void postRender(final Graphics g) {

  }

  @Override
  public void die() {

  }
}
