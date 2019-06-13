package me.kerdo.shootr.utils.save;

import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.weapon.MeleeWeapon;
import me.kerdo.shootr.weapon.RangedWeapon;
import me.kerdo.shootr.weapon.Weapon;

import java.awt.image.BufferedImage;

import static me.kerdo.shootr.weapon.Weapon.*;

public class SaveWeapon {
  public int id;
  public int type;
  public String name;
  public String description;
  public double damage;
  public int range;
  public int useTime;

  // If MeleeWeapon
  public double angle;
  public boolean autoSwing;

  // If RangedWeapon
  public int bulletSpeed;
  public int bulletSize;
  public int clipSize;
  public int reloadTime;
  public double inaccuracy;
  public int bullets;

  public SaveWeaponTexture texture;

  public Weapon toWeapon() {
    Weapon weapon = null;

    final BufferedImage[] textures = new BufferedImage[] {
            Assets.spritesheets.get(texture.spritesheet).crop(texture.xNH * 32, texture.yNH * 32, texture.sizeX, texture.sizeY),
            Assets.spritesheets.get(texture.spritesheet).crop(texture.xH * 32, texture.yH * 32, texture.sizeX, texture.sizeY)
    };

    if (type == PISTOL || type == RIFLE || type == SNIPER) {
      weapon = new RangedWeapon(id, type, name, description, textures, texture, damage, range, bulletSpeed, bulletSize, clipSize, reloadTime, inaccuracy, useTime);
      ((RangedWeapon) weapon).bullets = bullets;
    } else if (type == SWORD) {
      weapon = new MeleeWeapon(id, type, name, description, textures, texture, damage, range, useTime, angle, autoSwing);
    } else if (type == GRENADE) {
    }

    return weapon;
  }
}
