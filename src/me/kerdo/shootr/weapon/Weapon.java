package me.kerdo.shootr.weapon;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.utils.Utils;
import me.kerdo.shootr.utils.save.SaveWeapon;
import me.kerdo.shootr.utils.save.SaveWeaponTexture;

import java.awt.image.BufferedImage;

// FIXME: Fix this up, add stats, upgrade tree and more.
public class Weapon {
  public static final int PISTOL = 0, RIFLE = 1, SNIPER = 2, SWORD = 3, GRENADE = 4;

  public static final Weapon[] WEAPONS = new Weapon[5];

  private final int id;
  private final int type;
  private final String name;
  private final String description;
  private final BufferedImage[] textures;
  private final double damage;
  private final int range;
  private final int useTime;
  private final SaveWeaponTexture saveTexture;

  public Weapon(final int id, final int type, final String name, final String description, final BufferedImage[] textures, final SaveWeaponTexture saveTexture, final double damage, final int range, final int useTime) {
    this.id = id;
    this.type = type;
    this.name = name;
    this.description = description;
    this.textures = textures;
    this.saveTexture = saveTexture;
    this.damage = damage;
    this.range = range;
    this.useTime = useTime;

    WEAPONS[id] = this;
  }

  public static void init() {
    final String data = Utils.loadFileFromJAR("/data/weapons.json");

    final JsonParser parser = new JsonParser();
    final JsonElement root = parser.parse(data);
    final JsonArray weapons = root.getAsJsonArray();

    for (final JsonElement weaponEl : weapons) {
      final JsonObject weapon = weaponEl.getAsJsonObject();

      final int id = weapon.get("id").getAsInt();
      final int type = weapon.get("type").getAsInt();
      final String name = weapon.get("name").getAsString();
      final String description = weapon.get("description").getAsString();

      final JsonObject texture = weapon.get("texture").getAsJsonObject();
      final String textureName = texture.get("name").getAsString();
      final JsonArray textureCoords = texture.get("coords").getAsJsonArray();

      final JsonArray textureCoordsNH = textureCoords.get(0).getAsJsonArray(), textureCoordsH = textureCoords.get(1).getAsJsonArray();

      final int textureX = textureCoordsNH.get(0).getAsInt(), textureY = textureCoordsNH.get(1).getAsInt();
      final int textureXH = textureCoordsH.get(0).getAsInt(), textureYH = textureCoordsH.get(1).getAsInt();

      final JsonArray textureSize = texture.get("size").getAsJsonArray();
      final int sizeX = textureSize.get(0).getAsInt(), sizeY = textureSize.get(1).getAsInt();

      // TODO: Add other texture data to object for saving

      final BufferedImage textureImage = Assets.spritesheets.get(textureName).crop(textureX * 32, textureY * 32, sizeX, sizeY);
      final BufferedImage textureImageH = Assets.spritesheets.get(textureName).crop(textureXH * 32, textureYH * 32, sizeX, sizeY);

      final SaveWeaponTexture saveTexture = new SaveWeaponTexture();
      saveTexture.spritesheet = textureName;
      saveTexture.sizeX = sizeX;
      saveTexture.sizeY = sizeY;
      saveTexture.xNH = textureX;
      saveTexture.yNH = textureY;
      saveTexture.xH = textureXH;
      saveTexture.yH = textureYH;

      final double damage = weapon.get("damage").getAsDouble();
      final int range = weapon.get("range").getAsInt();
      final int useTime = weapon.get("useTime").getAsInt();

      if (type == PISTOL || type == RIFLE || type == SNIPER) {
        final int bulletSpeed = weapon.get("bulletSpeed").getAsInt();
        final int bulletSize = weapon.get("bulletSize").getAsInt();
        final int clipSize = weapon.get("clipSize").getAsInt();
        final int reloadTime = weapon.get("reloadTime").getAsInt();
        final double inaccuracy = weapon.get("inaccuracy").getAsInt();

        new RangedWeapon(id, type, name, description, new BufferedImage[] { textureImage, textureImageH }, saveTexture, damage, range, bulletSpeed, bulletSize, clipSize, reloadTime, inaccuracy, useTime);
      } else if (type == SWORD) {
        final double angle = weapon.get("angle").getAsDouble() * (Math.PI / 180);
        final boolean autoSwing = weapon.get("autoSwing").getAsBoolean();

        new MeleeWeapon(id, type, name, description, new BufferedImage[] { textureImage, textureImageH }, saveTexture, damage, range, useTime, angle, autoSwing);
      }
    }
  }

  public int getId() {
    return id;
  }

  public int getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public BufferedImage[] getTextures() {
    return textures;
  }

  public double getDamage() {
    return damage;
  }

  public int getRange() {
    return range;
  }

  public int getUseTime() {
    return useTime;
  }

  public SaveWeaponTexture getSaveTexture() {
    return saveTexture;
  }

  public SaveWeapon getSaveData() {
    final SaveWeapon saveWeapon = new SaveWeapon();
    saveWeapon.id = id;
    saveWeapon.type = type;
    saveWeapon.name = name;
    saveWeapon.description = description;
    saveWeapon.damage = damage;
    saveWeapon.range = range;
    saveWeapon.useTime = useTime;

    if (this instanceof MeleeWeapon) {
      final MeleeWeapon weapon = ((MeleeWeapon) this);

      saveWeapon.angle = weapon.getAngle();
      saveWeapon.autoSwing = weapon.isAutoSwing();
    } else if (this instanceof RangedWeapon) {
      final RangedWeapon weapon = ((RangedWeapon) this);

      saveWeapon.bulletSpeed = weapon.getBulletSpeed();
      saveWeapon.bulletSize = weapon.getBulletSize();
      saveWeapon.clipSize = weapon.getClipSize();
      saveWeapon.reloadTime = weapon.getReloadTime();
      saveWeapon.inaccuracy = weapon.getInaccuracy();
      saveWeapon.bullets = weapon.bullets;
    }

    saveWeapon.texture = getSaveTexture();

    return saveWeapon;
  }
}