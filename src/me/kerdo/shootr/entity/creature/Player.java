package me.kerdo.shootr.entity.creature;

import me.kerdo.shootr.Handler;
import me.kerdo.shootr.entity.character.Character;
import me.kerdo.shootr.entity.character.Stats;
import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.gfx.Text;
import me.kerdo.shootr.gfx.ui.inventory.Inventory;
import me.kerdo.shootr.gfx.ui.inventory.weapons.WeaponInventory;
import me.kerdo.shootr.world.Tile;

import java.awt.*;
import java.awt.event.KeyEvent;

import static me.kerdo.shootr.entity.character.CharacterAnimationProperties.*;

public class Player extends Creature {
  // TODO: Generalise this to the Creature class
  int lastDir = LEFT, dir = LEFT, state = STANDING;

  private Inventory inventory;
  private Character character;
  private WeaponInventory weaponInventory;
  // TODO: Add cooldown timer to JSON
  private boolean dashing = false;
  private int dashingTimer = 0, dashingCooldown = 2000;
  private long lastDash = System.currentTimeMillis();

  public Player(final Handler handler, final float x, final float y, final Character character) {
    super(handler, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT, 100);

    this.character = character;

    health = this.character.getStats().health;
    speed = this.character.getStats().speed;

    handler.getWorld().setPlayer(this);

    inventory = new Inventory(handler, handler.getWidth() - Inventory.WIDTH - 20, handler.getHeight() - Inventory.HEIGHT - 20, Inventory.WIDTH, Inventory.HEIGHT);
    weaponInventory = new WeaponInventory(handler, handler.getWidth() / 2 - 138, handler.getHeight() / 2 - 138, 276, 276, this);

    // TODO: Weapon choosing from mouse position and relative dragging
  }

  public void getInput() {
    xMove = 0;
    yMove = 0;

    if (handler.getKeyManager().isKeyPressed(KeyEvent.VK_W))
      yMove = -speed;
    if (handler.getKeyManager().isKeyPressed(KeyEvent.VK_S))
      yMove = +speed;
    if (handler.getKeyManager().isKeyPressed(KeyEvent.VK_A))
      xMove = -speed;
    if (handler.getKeyManager().isKeyPressed(KeyEvent.VK_D))
      xMove = +speed;

    if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_E))
      inventory.setVisible(!inventory.isVisible());

    if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_Q))
      weaponInventory.setVisible(!weaponInventory.isVisible());

    if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_SPACE) && character.getStats().playClass == Stats.NINJA) {
      if (!dashing && System.currentTimeMillis() - lastDash >= dashingCooldown) {
        dashing = true;
        speed *= 4;
      }
    }

    if (yMove < 0) {
      lastDir = dir;
      dir = UP;
    } else if (yMove > 0) {
      lastDir = dir;
      dir = DOWN;
    } else {
      if (xMove < 0) {
        lastDir = dir;
        dir = LEFT;
      } else {
        lastDir = dir;
        dir = RIGHT;
      }
    }

    if (xMove == 0 && yMove == 0) {
      dir = lastDir;
      state = STANDING;
    } else {
      state = MOVING;
    }
  }

  @Override
  public void tick(final double dt) {
    getInput();
    move(dt);
    handler.getCamera().centerOnEntity(this);

    if (dashing) {
      dashingTimer++;

      speed -= speed * .00002;

      if (dashingTimer > 85) {
        stopDash();
      }
    }

    character.getAnimations()[state][dir].tick(dt);
  }

  @Override
  public void render(final Graphics g) {
    g.drawImage(character.getAnimations()[state][dir].getCurrentFrame(), (int) (x - handler.getCamera().getxOff()), (int) (y - handler.getCamera().getyOff()), width, height, null);

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

    Text.drawString(g, "Dashing: " + dashing, 15, 110, false, Color.WHITE, Assets.andy16);
    Text.drawString(g, "Dashing cooldown: " + (2000 - (Math.min(System.currentTimeMillis() - lastDash, dashingCooldown))), 15, 130, false, Color.WHITE, Assets.andy16);
  }

  public void postRender(final Graphics g) {
  }

  @Override
  public void die() {
  }

  public void stopDash() {
    dashing = false;
    speed = character.getStats().speed;
    dashingTimer = 0;
    lastDash = System.currentTimeMillis();
  }

  public Inventory getInventory() {
    return inventory;
  }

  public WeaponInventory getWeaponInventory() {
    return weaponInventory;
  }

  public boolean isDashing() {
    return dashing;
  }

  public void setDashing(final boolean dashing) {
    this.dashing = dashing;
  }

  public Character getCharacter() {
    return character;
  }

  public void setDashingTimer(int dashingTimer) {
    this.dashingTimer = dashingTimer;
  }
}
