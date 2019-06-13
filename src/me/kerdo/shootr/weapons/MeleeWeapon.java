package me.kerdo.shootr.weapons;

import java.awt.image.BufferedImage;

public class MeleeWeapon extends Weapon {
  private final int useTime;
  private final double angle;
  private final boolean autoSwing;

  public MeleeWeapon(final int id, final int type, final String name, final String description, final BufferedImage[] textures, final double damage, final int range, final int useTime, final int angle, final boolean autoSwing) {
    super(id, type, name, description, textures, damage, range);
    this.useTime = useTime;
    this.angle = angle * (Math.PI / 180);
    this.autoSwing = autoSwing;
  }

  public int getUseTime() {
    return useTime;
  }

  public double getAngle() {
    return angle;
  }

  public boolean isAutoSwing() {
    return autoSwing;
  }
}
