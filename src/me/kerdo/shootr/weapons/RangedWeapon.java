package me.kerdo.shootr.weapons;

import java.awt.image.BufferedImage;

public class RangedWeapon extends Weapon {
  private final int bulletSpeed;
  private final int bulletSize;
  private final int clipSize;
  private final int reloadTime;
  private final double inaccuracy;
  private final int shootTime;

  public RangedWeapon(final int id, final int type, final String name, final String description, final BufferedImage texture, final double damage, final int range, final int bulletSpeed, final int bulletSize, final int clipSize, final int reloadTime, final double inaccuracy, final int shotsPerSecond) {
    super(id, type, name, description, texture, damage, range);

    this.bulletSpeed = bulletSpeed;
    this.bulletSize = bulletSize;
    this.clipSize = clipSize;
    this.reloadTime = reloadTime;
    this.inaccuracy = inaccuracy;
    this.shootTime = shotsPerSecond;
  }


  public int getBulletSpeed() {
    return bulletSpeed;
  }

  public int getBulletSize() {
    return bulletSize;
  }

  public int getClipSize() {
    return clipSize;
  }

  public int getReloadTime() {
    return reloadTime;
  }

  public double getInaccuracy() {
    return inaccuracy;
  }

  public int getShootTime() {
    return shootTime;
  }
}
