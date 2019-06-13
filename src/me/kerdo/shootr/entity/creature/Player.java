package me.kerdo.shootr.entity.creature;

import me.kerdo.shootr.Handler;
import me.kerdo.shootr.audio.Audio;
import me.kerdo.shootr.entity.Entity;
import me.kerdo.shootr.entity.character.Character;
import me.kerdo.shootr.entity.character.Stamina;
import me.kerdo.shootr.entity.character.Stats;
import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.gfx.Text;
import me.kerdo.shootr.gfx.ui.inventory.Inventory;
import me.kerdo.shootr.gfx.ui.inventory.weapons.WeaponInventory;
import me.kerdo.shootr.utils.Utils;
import me.kerdo.shootr.weapons.Bullet;
import me.kerdo.shootr.weapons.MeleeWeapon;
import me.kerdo.shootr.weapons.RangedWeapon;
import me.kerdo.shootr.weapons.Weapon;
import me.kerdo.shootr.world.Tile;

import java.awt.*;

import static java.awt.event.KeyEvent.*;
import static me.kerdo.shootr.entity.character.CharacterAnimationProperties.*;

public class Player extends Creature {
  // TODO: Generalise this to the Creature class
  int lastDir = LEFT, dir = LEFT, state = STANDING;

  private Inventory inventory;
  private Character character;

  // TODO: Add cooldown timer to JSON
  private boolean dashing = false;
  private int dashingTimer = 0, dashingCooldown = 2000;
  private long lastDash = System.currentTimeMillis();

  private Stamina stamina;
  private long staminaTimer;
  private boolean canRun = true, running = false;

  // Weapon
  private WeaponInventory weaponInventory = null;
  private boolean wiCanOpen = true;

  private Weapon weapon;
  private int bullets;
  private long useTimer, reloadTimer;
  private Weapon[] weapons = new Weapon[4];
  private boolean hasSwung = false, reloading = false;

  int[] totalBullets = new int[5];

  public Player(final Handler handler, final float x, final float y, final Character character) {
    super(handler, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT, 100);

    this.character = character;

    health = this.character.getStats().health;
    speed = this.character.getStats().speed;

    stamina = new Stamina();
    stamina.duration = character.getStats().stamina.duration;
    stamina.regeneration = character.getStats().stamina.regeneration;

    // TEMP
    // Weapon.WEAPONS[4] = new MeleeWeapon(4, 0, "Sword", "Testing", Assets.spritesheets.get("weapons").crop(0, 32, 64, 32), 10, 200, 1000, 45);

    weapons[0] = Weapon.WEAPONS[0];
    weapons[1] = null;
    weapons[2] = Weapon.WEAPONS[2];
    weapons[3] = Weapon.WEAPONS[1];

    handler.getWorld().setPlayer(this);

    inventory = new Inventory(handler, handler.getWidth() - Inventory.WIDTH - 20, handler.getHeight() - Inventory.HEIGHT - 20, Inventory.WIDTH, Inventory.HEIGHT);
    inventory.setVisible(false);

    setWeapon(0);

    // TEMP
    handler.getWorld().getEntityManager().addEntity(new Enemy(handler, 300, 300, 32, 32, 400));
    handler.getWorld().getEntityManager().addEntity(new Enemy(handler, 400, 300, 32, 32, 400));
    handler.getWorld().getEntityManager().addEntity(new Enemy(handler, 300, 400, 32, 32, 400));
    handler.getWorld().getEntityManager().addEntity(new Enemy(handler, 400, 400, 32, 32, 400));
  }

  public void getInput(final double dt) {
    xMove = 0;
    yMove = 0;

    if (handler.getKeyManager().isKeyPressed(VK_W))
      yMove = -speed;
    if (handler.getKeyManager().isKeyPressed(VK_S))
      yMove = +speed;
    if (handler.getKeyManager().isKeyPressed(VK_A))
      xMove = -speed;
    if (handler.getKeyManager().isKeyPressed(VK_D))
      xMove = +speed;

    if (handler.getKeyManager().keyJustPressed(VK_E))
      inventory.setVisible(!inventory.isVisible());

    if (handler.getMouseManager().isLeftPressed()) {
      final int mx = handler.getMouseManager().getMouseX(), my = handler.getMouseManager().getMouseY();

      attack(mx, my);
    } else {
      if (hasSwung)
        hasSwung = false;
    }


    if (handler.getKeyManager().keyJustPressed(VK_SPACE) && character.getStats().playClass == Stats.NINJA && !running) {
      if (!dashing && System.currentTimeMillis() - lastDash >= dashingCooldown) {
        dashing = true;
        speed *= 6;
      }
    }

    if (!canRun) {
      stamina.duration = (int) ((System.currentTimeMillis() - staminaTimer) * ((double) character.getStats().stamina.duration / (double) character.getStats().stamina.regeneration));

      // System.out.println(((double) character.getStats().stamina.duration / (double) character.getStats().stamina.regeneration));

      if (System.currentTimeMillis() - staminaTimer > stamina.regeneration) {
        canRun = true;
        staminaTimer = System.currentTimeMillis();
        stamina.duration = character.getStats().stamina.duration;
      }
    }

    if (handler.getKeyManager().isKeyPressed(VK_SHIFT) && canRun && stamina.duration > 0 && (xMove != 0 || yMove != 0) && !dashing) {
      running = true;

      speed = character.getStats().speed * 3;
      stamina.duration -= dt * 1000;

      if (stamina.duration <= 0) {
        canRun = false;
        staminaTimer = System.currentTimeMillis();
        speed = character.getStats().speed;
      }

    } else {
      if (running) {
        running = false;
        speed = character.getStats().speed;
      }
    }

    if (handler.getMouseManager().isRightPressed()) {
      if (wiCanOpen) {
        wiCanOpen = false;

        if (weaponInventory == null) {
          weaponInventory = new WeaponInventory(handler, handler.getMouseManager().getMouseX() - 212, handler.getMouseManager().getMouseY() - 138, 424, 276, this, weapons);
          weaponInventory.setVisible(true);
          handler.getGame().getMenuManager().getMenu().getUiManager().addObject(weaponInventory);
        }
      }
    } else {
      wiCanOpen = true;

      if (weaponInventory != null) {
        final int range = 64;
        final int wix = weaponInventory.getX() + weaponInventory.getWidth() / 2, wiy = weaponInventory.getY() + weaponInventory.getHeight() / 2;
        final int mx = handler.getMouseManager().getMouseX(), my = handler.getMouseManager().getMouseY();

        final boolean xBound = mx > wix - range && mx < wix + range;
        final boolean yBound = my > wiy - range && my < wiy + range;

        if (my < wiy - range && xBound)
          setWeapon(0);

        if (mx > wix + range && yBound)
          setWeapon(1);

        if (my > wiy + range && xBound)
          setWeapon(2);

        if (mx < wix - range && yBound)
          setWeapon(3);

        disableWeaponInventory();
      }
    }

    if (handler.getKeyManager().keyJustPressed(VK_ENTER))
      hurt(3);

    if (handler.getKeyManager().keyJustPressed(VK_R)) {
      reloading = true;
      reloadTimer = System.currentTimeMillis();
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
    getInput(dt);
    move(dt);
    handler.getCamera().centerOnEntity(this);

    if (dashing) {
      dashingTimer++;

      speed -= speed * dt;

      if (dashingTimer > 40) {
        stopDash();
      }
    }

    if (reloading) {
      if (!(weapon instanceof RangedWeapon))
        return;

      if (System.currentTimeMillis() - reloadTimer > ((RangedWeapon) weapon).getReloadTime()) {
        bullets = ((RangedWeapon) weapon).getClipSize();
        reloading = false;
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
    Text.drawString(g, "Movement speed: " + speed, 15, 150, false, Color.WHITE, Assets.andy16);

    Text.drawString(g, "Can run: " + canRun, 15, 170, false, Color.WHITE, Assets.andy16);
    Text.drawString(g, "Stamina duration: " + stamina.duration, 15, 190, false, Color.WHITE, Assets.andy16);

    Text.drawString(g, "Reloading: " + reloading, 15, 210, false, Color.WHITE, Assets.andy16);
    Text.drawString(g, "Reload timer: " + ((weapon instanceof RangedWeapon) ? ((RangedWeapon) weapon).getReloadTime() - Math.min(System.currentTimeMillis() - reloadTimer, ((RangedWeapon) weapon).getReloadTime()) : ((MeleeWeapon) weapon).getUseTime() - Math.min(System.currentTimeMillis() - useTimer, ((MeleeWeapon) weapon).getUseTime())), 15, 230, false, Color.WHITE, Assets.andy16);


    // Health
    g.drawImage(Assets.heart, 20, handler.getHeight() - 84, 64, 64, null);
    Text.drawString(g, ((int) Utils.limitPrecision(((double) health / maxHealth) * 100, 0)) + "%", 52, handler.getHeight() - 62, true, Color.WHITE, Assets.andy16);

    // Stamina
    g.drawImage(Assets.stamina, 94, handler.getHeight() - 84, 64, 64, null);
    Text.drawString(g, ((int) Utils.limitPrecision(((double) stamina.duration / character.getStats().stamina.duration) * 100, 0)) + "%", 126, handler.getHeight() - 62, true, Color.WHITE, Assets.andy16);

    // TEMP
    // Draw from mouse angle

    if (weapon instanceof MeleeWeapon) {
      final int mx = handler.getMouseManager().getMouseX(), my = handler.getMouseManager().getMouseY();
      final int xx = (int) (x + width / 2 - handler.getCamera().getxOff());
      final int yy = (int) (y + height / 2 - handler.getCamera().getyOff());
      final double angle = Math.atan2(my - yy, mx - xx);

      final int nx = (int) (weapon.getRange() * Math.cos(angle) + xx);
      final int ny = (int) (weapon.getRange() * Math.sin(angle) + yy);

      final int nx1 = (int) (weapon.getRange() * Math.cos(angle - ((MeleeWeapon) weapon).getAngle()) + xx);
      final int ny1 = (int) (weapon.getRange() * Math.sin(angle - ((MeleeWeapon) weapon).getAngle()) + yy);

      final int nx2 = (int) (weapon.getRange() * Math.cos(angle + ((MeleeWeapon) weapon).getAngle()) + xx);
      final int ny2 = (int) (weapon.getRange() * Math.sin(angle + ((MeleeWeapon) weapon).getAngle()) + yy);

      g.setColor(Color.RED);
   /* g.drawLine(xx, yy, nx, ny);
    g.drawLine(xx, yy, nx1, ny1);
    g.drawLine(xx, yy, nx2, ny2);

    g.drawLine(nx, ny, nx1, ny1);
    g.drawLine(nx, ny, nx2, ny2);*/

      Polygon p = new Polygon(new int[] { xx, nx1, nx, nx2 }, new int[] { yy, ny1, ny, ny2 }, 4);
      g.drawPolygon(p);
    }

    // Current weapon HUD
    // Image, ammo

    // Add graphical reload timer
    if (weapon instanceof RangedWeapon) {
      g.drawImage(weapon.getTextures()[0], 20, handler.getHeight() - 158, weapon.getTextures()[0].getWidth() * 2, 64, null);
      Text.drawString(g, bullets + "/" + ((RangedWeapon) weapon).getClipSize(), 20, handler.getHeight() - 178, false, (reloading) ? Color.RED : Color.WHITE, Assets.andy32);
    } else {
      g.drawImage(weapon.getTextures()[System.currentTimeMillis() - useTimer > ((MeleeWeapon) weapon).getUseTime() ? 0 : 1], 20, handler.getHeight() - 158, weapon.getTextures()[0].getWidth() * 2, 64, null);
    }
  }

  public void postRender(final Graphics g) {
  }

  @Override
  public void die() {
  }

  private void attack(final int mx, final int my) {
    if (weapon instanceof RangedWeapon) {
      if (System.currentTimeMillis() - useTimer > ((RangedWeapon) weapon).getUseTime()) {
        useTimer = System.currentTimeMillis();

        shoot(mx, my);
      }
    } else if (weapon instanceof MeleeWeapon) {
      if (System.currentTimeMillis() - useTimer > ((MeleeWeapon) weapon).getUseTime() && (((MeleeWeapon) weapon).isAutoSwing() || !hasSwung)) {
        hasSwung = true;

        useTimer = System.currentTimeMillis();

        slice(mx, my);
      }
    }
  }

  private void shoot(final int mx, final int my) {
    if (reloading)
      return;

    // TODO: Use clip size and reloading mechanics
    final int xx = (int) (x + width / 2 - handler.getCamera().getxOff());
    final int yy = (int) (y + height / 2 - handler.getCamera().getyOff());
    final double angle = Math.atan2(my - yy, mx - xx);

    final int size = ((RangedWeapon) weapon).getBulletSize();
    final float bx = x + width / 2 - size;
    final float by = y + height / 2 - size;

    handler.getWorld().getBulletManager().addBullet(new Bullet(handler, angle, bx, by, (RangedWeapon) weapon));

    bullets--;

    if (bullets <= 0) {
      reloading = true;
      reloadTimer = System.currentTimeMillis();
    }

    Audio.playSound(Assets.gunshot, -17.5f);
  }

  private void slice(final int mx, final int my) {
    for (final Entity e : handler.getWorld().getEntityManager().getEntities()) {
      if (e == this)
        continue;

      if (insideWeaponBounds(e))
        e.hurt((int) weapon.getDamage());
    }
  }

  public boolean insideWeaponBounds(final Entity e) {
    final int mx = handler.getMouseManager().getMouseX(), my = handler.getMouseManager().getMouseY();
    final int xx = (int) (x + width / 2 - handler.getCamera().getxOff());
    final int yy = (int) (y + height / 2 - handler.getCamera().getyOff());
    final double angle = Math.atan2(my - yy, mx - xx);

    final int nx = (int) (weapon.getRange() * Math.cos(angle) + xx);
    final int ny = (int) (weapon.getRange() * Math.sin(angle) + yy);

    final int nx1 = (int) (weapon.getRange() * Math.cos(angle - ((MeleeWeapon) weapon).getAngle()) + xx);
    final int ny1 = (int) (weapon.getRange() * Math.sin(angle - ((MeleeWeapon) weapon).getAngle()) + yy);

    final int nx2 = (int) (weapon.getRange() * Math.cos(angle + ((MeleeWeapon) weapon).getAngle()) + xx);
    final int ny2 = (int) (weapon.getRange() * Math.sin(angle + ((MeleeWeapon) weapon).getAngle()) + yy);

    Polygon p = new Polygon(new int[] { xx, nx1, nx, nx2 }, new int[] { yy, ny1, ny, ny2 }, 4);

    return p.intersects(new Rectangle((int) (e.getX() - handler.getCamera().getxOff()), (int) (e.getY() - handler.getCamera().getyOff()), e.getWidth(), e.getHeight()));
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

  public void setWeapon(final int id) {
    if (weapons[id] == null || weapon == weapons[id])
      return;

    weapon = weapons[id];

    if (weapon instanceof RangedWeapon) {
      // TODO: Later remember current bullets and total ammo
      bullets = ((RangedWeapon) weapon).getClipSize();
    }

    System.out.println("Selected weapon with id " + id + ", name: " + this.weapon.getName());

    wiCanOpen = false;
  }

  private void disableWeaponInventory() {
    handler.getGame().getMenuManager().getMenu().getUiManager().removeObject(weaponInventory);
    weaponInventory = null;
  }
}
