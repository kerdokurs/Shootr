package me.kerdo.shootr.entity;

import me.kerdo.shootr.Handler;
import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.gfx.Text;

import java.awt.*;

public abstract class Entity {
  public static final int DEFAULT_HEALTH = 100;

  protected final Handler handler;
  protected float x, y;
  protected final int width, height;
  protected int maxHealth, health;
  protected boolean active = true;
  protected Rectangle bounds;

  public Entity(final Handler handler, final float x, final float y, final int width, final int height, final int maxHealth) {
    this.handler = handler;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;

    this.maxHealth = maxHealth;
    if (maxHealth == 0)
      this.maxHealth = DEFAULT_HEALTH;

    health = maxHealth;

    bounds = new Rectangle(0, 0, width, height);
  }

  public abstract void tick(final double dt);

  public abstract void render(final Graphics g);

  public abstract void die();

  public void displayHealth(final Graphics g) {
    Text.drawString(g, health + "/" + maxHealth, (int) (x - handler.getCamera().getxOff() + 5), (int) (y - handler.getCamera().getyOff() + 15), false, Color.WHITE, Assets.andy16);
  }

  public void hurt(final int dmg) {
    health -= dmg;
    if (health <= 0) {
      active = false;
      die();
    }
  }

  public boolean checkEntityCollisions(final float xOff, final float yOff) {
    for (Entity e : handler.getWorld().getEntityManager().getEntities()) {
      if (e.equals(this))
        continue;

      if (e.getCollisionBounds(0, 0).intersects(getCollisionBounds(xOff, yOff))) {
        return true;
      }
    }
    return false;
  }

  public Rectangle getCollisionBounds(final float xOff, final float yOff) {
    return new Rectangle((int) (x + bounds.x + xOff), (int) (y + bounds.y + yOff), bounds.width, bounds.height);
  }

  public float getX() {
    return x;
  }

  public void setX(final float x) {
    this.x = x;
  }

  public float getY() {
    return y;
  }

  public void setY(final float y) {
    this.y = y;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public int getHealth() {
    return health;
  }

  public void setHealth(int health) {
    this.health = health;
  }

  public int getMaxHealth() {
    return maxHealth;
  }

  public void setMaxHealth(final int maxHealth) {
    this.maxHealth = maxHealth;
  }

  public Rectangle getBounds() {
    return bounds;
  }

  public void setBounds(Rectangle bounds) {
    this.bounds = bounds;
  }

  public boolean isActive() {
    return active;
  }
}
