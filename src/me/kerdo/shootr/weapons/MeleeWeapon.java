package me.kerdo.shootr.weapons;

import java.awt.image.BufferedImage;

public class MeleeWeapon extends Weapon {
  private final int useTime;

  public MeleeWeapon(final int id, final int type, final String name, final String description, final BufferedImage texture, final double damage, final int range, final int useTime) {
    super(id, type, name, description, texture, damage, range);
    this.useTime = useTime;
  }

  public int getUseTime() {
    return useTime;
  }
}
