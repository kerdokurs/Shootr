package me.kerdo.shootr.entity.creature;

import me.kerdo.shootr.Handler;
import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.gfx.Text;
import me.kerdo.shootr.gfx.ui.inventory.Inventory;
import me.kerdo.shootr.world.Tile;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Player extends Creature {
  private BufferedImage texture;
  private Inventory inventory;

  public Player(final Handler handler, final float x, final float y) {
    super(handler, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT, 100);
    texture = Assets.spritesheets.get("default").crop32(0, 1);

    handler.getWorld().setPlayer(this);

    inventory = new Inventory(handler, handler.getWidth() - Inventory.WIDTH - 20, handler.getHeight() - Inventory.HEIGHT - 20, Inventory.WIDTH, Inventory.HEIGHT);
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
      xMove = +speed;

    if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_E))
      inventory.setVisible(!inventory.isVisible());
  }

  @Override
  public void tick(final double dt) {
    getInput();
    move(dt);
    handler.getCamera().centerOnEntity(this);
  }

  @Override
  public void render(final Graphics g) {
    g.drawImage(texture, (int) (x - handler.getCamera().getxOff()), (int) (y - handler.getCamera().getyOff()), width, height, null);

    g.setColor(Color.WHITE);
    g.drawLine((int) (x - handler.getCamera().getxOff() + width / 2),
            (int) (y - handler.getCamera().getyOff() + height / 2), handler.getGame().getMouseManager().getMouseX(),
            handler.getGame().getMouseManager().getMouseY());

    g.drawRect((int) (x - handler.getCamera().getxOff() - bounds.x), (int) (y - handler.getCamera().getyOff() - bounds.y), bounds.width, bounds.height);

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

  public Inventory getInventory() {
    return inventory;
  }
}
