package me.kerdo.shootr.weapons;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.utils.Utils;

import java.awt.image.BufferedImage;
import java.util.Random;

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

  public Weapon(final int id, final int type, final String name, final String description, final BufferedImage[] textures, final double damage, final int range) {
    this.id = id;
    this.type = type;
    this.name = name;
    this.description = description;
    this.textures = textures;
    this.damage = damage;
    this.range = range;

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

      final BufferedImage textureImage = Assets.spritesheets.get(textureName).crop(textureX * 32, textureY * 32, sizeX, sizeY);
      final BufferedImage textureImageH = Assets.spritesheets.get(textureName).crop(textureXH * 32, textureYH * 32, sizeX, sizeY);

      final double damage = weapon.get("damage").getAsDouble();
      final int range = weapon.get("range").getAsInt();
      final int useTime = weapon.get("useTime").getAsInt();

      if (type == PISTOL || type == RIFLE || type == SNIPER) {
        final int bulletSpeed = weapon.get("bulletSpeed").getAsInt();
        final int bulletSize = weapon.get("bulletSize").getAsInt();
        final int clipSize = weapon.get("clipSize").getAsInt();
        final int reloadTime = weapon.get("reloadTime").getAsInt();
        final double inaccuracy = weapon.get("inaccuracy").getAsInt();

        new RangedWeapon(id, type, name, description, new BufferedImage[] { textureImage, textureImageH }, damage, range, bulletSpeed, bulletSize, clipSize, reloadTime, inaccuracy, useTime);
      } else if (type == SWORD) {
        final int angle = weapon.get("angle").getAsInt();
        final boolean autoSwing = weapon.get("autoSwing").getAsBoolean();

        new MeleeWeapon(id, type, name, description, new BufferedImage[] { textureImage, textureImageH }, damage, range, useTime, angle, autoSwing);
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
}