package me.kerdo.shootr.weapon;

import me.kerdo.shootr.utils.save.SaveWeaponTexture;

import java.awt.image.BufferedImage;

public class MeleeWeapon extends Weapon {
  private final double angle;
  private final boolean autoSwing;

  public MeleeWeapon(final int id, final int type, final String name, final String description, final BufferedImage[] textures, final SaveWeaponTexture saveTexture, final double damage, final int range, final int useTime, final double angle, final boolean autoSwing) {
    super(id, type, name, description, textures, saveTexture, damage, range, useTime);
    this.angle = angle;
    this.autoSwing = autoSwing;
  }

  public double getAngle() {
    return angle;
  }

  public boolean isAutoSwing() {
    return autoSwing;
  }
}
