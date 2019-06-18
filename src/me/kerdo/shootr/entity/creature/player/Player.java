package me.kerdo.shootr.entity.creature.player;

import com.google.gson.Gson;
import me.kerdo.shootr.Handler;
import me.kerdo.shootr.entity.Entity;
import me.kerdo.shootr.entity.character.Character;
import me.kerdo.shootr.entity.character.Stamina;
import me.kerdo.shootr.entity.character.Stats;
import me.kerdo.shootr.entity.creature.Creature;
import me.kerdo.shootr.entity.creature.player.panel.ControlPanel;
import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.gfx.Text;
import me.kerdo.shootr.gfx.ui.UIObject;
import me.kerdo.shootr.entity.creature.player.inventory.Inventory;
import me.kerdo.shootr.entity.creature.player.inventory.weapons.WeaponInventory;
import me.kerdo.shootr.utils.UIFunctions;
import me.kerdo.shootr.utils.Utils;
import me.kerdo.shootr.utils.save.SaveObject;
import me.kerdo.shootr.utils.save.SavePlayer;
import me.kerdo.shootr.utils.save.SaveWeapon;
import me.kerdo.shootr.weapon.Bullet;
import me.kerdo.shootr.weapon.MeleeWeapon;
import me.kerdo.shootr.weapon.RangedWeapon;
import me.kerdo.shootr.weapon.Weapon;
import me.kerdo.shootr.world.Tile;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static java.awt.event.KeyEvent.*;
import static me.kerdo.shootr.entity.character.CharacterAnimationProperties.*;

public class Player extends Creature {
  // TODO: Generalise this to the Creature class
  int lastDir = LEFT, dir = LEFT, state = STANDING;

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
  private int selectedWeaponIndex = 0;
  private long useTimer, reloadTimer;
  private Weapon[] weapons = new Weapon[4];
  private boolean hasSwung = false, reloading = false;
  private final int[] bullets;

  // UI
  private final List<UIObject> uiObjects = new ArrayList<>();

  // Control Panel
  private ControlPanel controlPanel;

  public Player(final Handler handler, final SavePlayer savePlayer) {
    super(handler, savePlayer.x, savePlayer.y, DEFAULT_WIDTH, DEFAULT_HEIGHT, savePlayer.maxHealth);

    health = savePlayer.health;
    stamina = savePlayer.stamina;

    // TODO: Fully implement saving and loading
    for (int i = 0; i < 4; i++) {
      if (savePlayer.weapons[i] == null)
        continue;

      final Weapon weapon = savePlayer.weapons[i].toWeapon();
      weapons[i] = weapon;
    }
    bullets = savePlayer.bullets;

    setWeapon(savePlayer.selectedWeaponIndex);

    this.character = Character.CHARACTERS[savePlayer.characterId];

    init();
  }

  public void init() {
    controlPanel = new ControlPanel(handler, 80, 80, handler.getWidth() - 160, handler.getHeight() - 160, this);
    uiObjects.add(controlPanel);
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

    if (handler.getKeyManager().keyJustPressed(VK_TAB))
      controlPanel.setVisible(!controlPanel.isVisible());

    if (handler.getMouseManager().isLeftPressed()&&  !controlPanel.isHovering()) {
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

    if (handler.getMouseManager().isRightPressed() && !controlPanel.isHovering()) {
      if (wiCanOpen) {
        wiCanOpen = false;

        if (weaponInventory == null) {
          weaponInventory = new WeaponInventory(handler, handler.getMouseManager().getMouseX() - 212, handler.getMouseManager().getMouseY() - 138, 424, 276, this, weapons);
          weaponInventory.setVisible(true);
          uiObjects.add(weaponInventory);
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

    if (handler.getKeyManager().keyJustPressed(VK_G)) {
      final SaveObject obj = getSaveData();
      final Gson gson = new Gson();

      try {
        PrintWriter writer = new PrintWriter(new File(System.getProperty("user.home") + "\\data.json"));
        writer.write(gson.toJson(obj));
        writer.flush();
        writer.close();
        System.exit(1);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }

    if (handler.getKeyManager().keyJustPressed(VK_ENTER))
      hurt(3);

    if (handler.getKeyManager().keyJustPressed(VK_R)) {
      reload();
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
        final int clipSize = ((RangedWeapon) weapon).getClipSize();
        final int typeId = weapon.getType();
        final int neededBullets = clipSize - ((RangedWeapon) weapon).bullets;

        if (bullets[typeId] >= neededBullets) {
          ((RangedWeapon) weapon).bullets += neededBullets;
          bullets[weapon.getType()] -= neededBullets;
        } else {
          ((RangedWeapon) weapon).bullets = bullets[typeId];
          bullets[typeId] = 0;
        }

        reloading = false;
      }
    }

    character.getAnimations()[state][dir].tick(dt);

    for (final UIObject object : new ArrayList<>(uiObjects)) {
      if (object.isVisible())
        object.tick(dt);
    }
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
    Text.drawString(g, "Reload timer: " + ((weapon instanceof RangedWeapon) ? ((RangedWeapon) weapon).getReloadTime() - Math.min(System.currentTimeMillis() - reloadTimer, ((RangedWeapon) weapon).getReloadTime()) : weapon.getUseTime() - Math.min(System.currentTimeMillis() - useTimer, weapon.getUseTime())), 15, 230, false, Color.WHITE, Assets.andy16);


    // Health
    g.drawImage(Assets.heart, 20, handler.getHeight() - 84, 64, 64, null);
    Text.drawString(g, ((int) Utils.limitPrecision(((double) health / maxHealth) * 100, 0)) + "%", 52, handler.getHeight() - 62, true, Color.WHITE, Assets.andy16);

    // Stamina
    g.drawImage(Assets.stamina, 94, handler.getHeight() - 84, 64, 64, null);
    Text.drawString(g, ((int) Utils.limitPrecision(((double) stamina.duration / character.getStats().stamina.duration) * 100, 0)) + "%", 126, handler.getHeight() - 62, true, Color.WHITE, Assets.andy16);

    // TEMP
    // Draw from mouse angle

    if (weapon instanceof MeleeWeapon) {
      g.setColor(Color.RED);
      g.drawPolygon(getHitPolygon());
    }

    // Current weapon HUD
    // Image, ammo

    // Add graphical reload timer
    if (weapon instanceof RangedWeapon) {
      g.drawImage(weapon.getTextures()[0], 20, handler.getHeight() - 158, weapon.getTextures()[0].getWidth() * 2, 64, null);
      Text.drawString(g, ((RangedWeapon) weapon).bullets + "/" + bullets[weapon.getType()], 20, handler.getHeight() - 178, false, (reloading) ? Color.RED : Color.WHITE, Assets.andy32);
    } else {
      g.drawImage(weapon.getTextures()[System.currentTimeMillis() - useTimer > weapon.getUseTime() ? 0 : 1], 20, handler.getHeight() - 158, weapon.getTextures()[0].getWidth() * 2, 64, null);
    }
  }

  public void postRender(final Graphics g) {
    for (final UIObject object : new ArrayList<>(uiObjects)) {
      if (object.isVisible())
        object.render(g);
    }
  }

  @Override
  public void die() {
  }

  private void attack(final int mx, final int my) {
    if (weapon instanceof RangedWeapon) {
      if (System.currentTimeMillis() - useTimer > weapon.getUseTime()) {
        useTimer = System.currentTimeMillis();

        shoot(mx, my);
      }
    } else if (weapon instanceof MeleeWeapon) {
      if (System.currentTimeMillis() - useTimer > weapon.getUseTime() && (((MeleeWeapon) weapon).isAutoSwing() || !hasSwung)) {
        hasSwung = true;

        useTimer = System.currentTimeMillis();

        slice(mx, my);
      }
    }
  }

  private void shoot(final int mx, final int my) {
    if (!reloading) {
      if (((RangedWeapon) weapon).bullets <= 0) {
        reload();
      } else {
        ((RangedWeapon) weapon).bullets--;

        // TODO: Use clip size and reloading mechanics
        final int xx = (int) (x + width / 2 - handler.getCamera().getxOff());
        final int yy = (int) (y + height / 2 - handler.getCamera().getyOff());
        final double angle = Math.atan2(my - yy, mx - xx);

        final int size = ((RangedWeapon) weapon).getBulletSize();
        final double bx = x + width / 2 - size;
        final double by = y + height / 2 - size;

        handler.getWorld().getBulletManager().addBullet(new Bullet(handler, angle, bx, by, (RangedWeapon) weapon));

        // Audio.playSound(Assets.gunshot, -17.5f);
      }
    }
  }

  private void reload() {
    if (weapon instanceof RangedWeapon && ((RangedWeapon) weapon).bullets < ((RangedWeapon) weapon).getClipSize() && bullets[weapon.getType()] > 0) {
      reloading = true;
      reloadTimer = System.currentTimeMillis();
    }
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
    return getHitPolygon().intersects(new Rectangle((int) (e.getX() - handler.getCamera().getxOff()), (int) (e.getY() - handler.getCamera().getyOff()), e.getWidth(), e.getHeight()));
  }

  private Polygon getHitPolygon() {
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

    return new Polygon(new int[] { xx, nx1, nx, nx2 }, new int[] { yy, ny1, ny, ny2 }, 4);
  }

  public void stopDash() {
    dashing = false;
    speed = character.getStats().speed;
    dashingTimer = 0;
    lastDash = System.currentTimeMillis();
  }

  public WeaponInventory getWeaponInventory() {
    return weaponInventory;
  }

  public ControlPanel getControlPanel() {
    return controlPanel;
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
    selectedWeaponIndex = id;

    if (weapon instanceof RangedWeapon) {
      // TODO: Later remember current bullets and total ammo
      // ((RangedWeapon) weapon).bullets = ((RangedWeapon) weapon).getClipSize();
    }

    System.out.println("Selected weapon with id " + id + ", name: " + this.weapon.getName());

    wiCanOpen = false;
  }

  private void disableWeaponInventory() {
    uiObjects.remove(weaponInventory);
    weaponInventory = null;
  }

  public SaveObject getSaveData() {
    final SaveObject object = new SaveObject();
    object.player = new SavePlayer();

    object.player.health = health;
    object.player.maxHealth = maxHealth;

    object.player.x = x;
    object.player.y = y;

    object.player.stamina = stamina;

    object.player.weapons = new SaveWeapon[4];
    for (int i = 0; i < 4; i++) {
      if (weapons[i] != null)
        object.player.weapons[i] = weapons[i].getSaveData();
    }
    object.player.selectedWeaponIndex = selectedWeaponIndex;

    object.player.bullets = bullets;

    return object;
  }

  public Weapon[] getWeapons() {
    return weapons;
  }

  public void mousePressed(final MouseEvent e) {
    UIFunctions.mousePressed(e, uiObjects);
  }

  public void mouseReleased(final MouseEvent e) {
    UIFunctions.mouseReleased(e, uiObjects);
  }

  public void mouseMoved(final MouseEvent e) {
    UIFunctions.mouseMoved(e, uiObjects);
  }

  public void mouseWheelMoved(final MouseWheelEvent e) {
    UIFunctions.mouseWheelMoved(e, uiObjects);
  }

  public void mouseDragged(final MouseEvent e) {
    UIFunctions.mouseDragged(e, uiObjects);
  }

  public void mouseClicked(final MouseEvent e) {
    UIFunctions.mouseClicked(e, uiObjects);
  }

  public void mouseEntered(final MouseEvent e) {
    UIFunctions.mouseEntered(e, uiObjects);
  }

  public void mouseExited(final MouseEvent e) {
    UIFunctions.mouseExited(e, uiObjects);
  }
}
