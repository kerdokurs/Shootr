package me.kerdo.shootr.weapons;

import me.kerdo.shootr.Handler;

import java.awt.*;

public class Bullet {
  private final Handler handler;
  private final double angle;
  private double x;
  private double y;
  private final double speed;
  private final int size;
  private final RangedWeapon weapon;

  private final double dx, dy;

  public Bullet(final Handler handler, final double angle, final double x, final double y, final RangedWeapon weapon) {
    this.handler = handler;
    this.angle = angle;
    this.x = x;
    this.y = y;
    this.speed = weapon.getBulletSpeed();
    this.size = weapon.getBulletSize();
    this.weapon = weapon;

    dx = Math.cos(angle) * speed;
    dy = Math.sin(angle) * speed;
  }

  public boolean tick(final double dt) {
    x += dx * dt;
    y += dy * dt;

    return false;
  }

  public void render(final Graphics g) {
    g.setColor(Color.WHITE);
    g.fillOval((int) (x - handler.getCamera().getxOff()), (int) (y - handler.getCamera().getyOff()), size, size);
  }

  public double getAngle() {
    return angle;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public double getSpeed() {
    return speed;
  }

  public int getSize() {
    return size;
  }

  public double getDamage() {
    return weapon.getDamage();
  }

  public RangedWeapon getWeapon() {
    return weapon;
  }
}
