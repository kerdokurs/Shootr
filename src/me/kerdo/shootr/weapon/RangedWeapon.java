package me.kerdo.shootr.weapon;

import me.kerdo.shootr.utils.save.SaveWeaponTexture;

import java.awt.image.BufferedImage;

public class RangedWeapon extends Weapon {
  private final int bulletSpeed;
  private final int bulletSize;
  private final int clipSize;
  private final int reloadTime;
  private final double inaccuracy;

  public int bullets;

  public RangedWeapon(final int id, final int type, final String name, final String description, final BufferedImage[] textures, final SaveWeaponTexture saveTexture, final double damage, final int range, final int bulletSpeed, final int bulletSize, final int clipSize, final int reloadTime, final double inaccuracy, final int useTime) {
    super(id, type, name, description, textures, saveTexture, damage, range, useTime);

    this.bulletSpeed = bulletSpeed;
    this.bulletSize = bulletSize;
    this.clipSize = clipSize;
    this.reloadTime = reloadTime;
    this.inaccuracy = inaccuracy;

    bullets = clipSize;
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
}
