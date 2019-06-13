package me.kerdo.shootr.entity.character;

import com.google.gson.*;
import me.kerdo.shootr.gfx.Animation;
import me.kerdo.shootr.gfx.ImageLoader;
import me.kerdo.shootr.gfx.Spritesheet;
import me.kerdo.shootr.utils.Utils;

import java.awt.image.BufferedImage;

import static me.kerdo.shootr.entity.character.CharacterAnimationProperties.*;

public class Character {
  public static final Character[] CHARACTERS = new Character[2];

  private int id;
  private final String name;
  private final Animation[][] animations;
  private Animation portrait;
  private final Stats stats;
  private final Perks perks;

  public Character(final int id, final String name, final Animation[][] animations, final Animation portrait, final Stats stats, final Perks perks) {
    this.id = id;
    this.name = name;
    this.animations = animations;
    this.portrait = portrait;
    this.stats = stats;
    this.perks = perks;

    CHARACTERS[id] = this;
  }

  public static void init() {
    final String data = Utils.loadFileFromJAR("/data/characters.json");

    final JsonParser parser = new JsonParser();
    final JsonElement root = parser.parse(data);
    final JsonArray characters = root.getAsJsonArray();

    final Gson gson = new Gson();

    for (final JsonElement el : characters) {
      final JsonObject character = el.getAsJsonObject();

      final int id = character.get("id").getAsInt();
      final String name = character.get("name").getAsString();

      final JsonObject texture = character.get("texture").getAsJsonObject();
      final String textureLocation = texture.get("location").getAsString();

      final JsonArray textureSize = texture.get("size").getAsJsonArray();
      final int textureSizeX = textureSize.get(0).getAsInt(), textureSizeY = textureSize.get(1).getAsInt();

      final int animSpeed = texture.get("speed").getAsInt();

      final JsonObject portrait = texture.get("portrait").getAsJsonObject();
      final int portraitAnimSpeed = portrait.get("speed").getAsInt();
      final JsonArray portraitTextureSize = portrait.get("size").getAsJsonArray();
      final int portraitTextureSizeX = portraitTextureSize.get(0).getAsInt(), portraitTextureSizeY = portraitTextureSize.get(1).getAsInt();

      final Stats stats = gson.fromJson(character.get("stats").getAsJsonObject(), Stats.class);
      final Perks perks = gson.fromJson(character.get("perks").getAsJsonObject(), Perks.class);

      final Spritesheet standingSheet = new Spritesheet(ImageLoader.loadImage("/textures/characters/" + textureLocation + "/standing.png"));
      final Spritesheet movingSheet = new Spritesheet(ImageLoader.loadImage("/textures/characters/" + textureLocation + "/moving.png"));
      final Spritesheet portraitSheet = new Spritesheet(ImageLoader.loadImage("/textures/characters/" + textureLocation + "/portrait.png"));

      final Animation[][] animations = new Animation[2][4];

      for (int layer = 0; layer < 4; layer++)
        animations[STANDING][layer] = toAnimation(standingSheet, textureSizeX, textureSizeY, animSpeed, layer);

      for (int layer = 0; layer < 4; layer++)
        animations[MOVING][layer] = toAnimation(movingSheet, textureSizeX, textureSizeY, animSpeed, layer);

      new Character(id, name, animations, toAnimation(portraitSheet, portraitTextureSizeX, portraitTextureSizeY, portraitAnimSpeed, 0), stats, perks);
    }
  }

  private static Animation toAnimation(final Spritesheet spritesheet, final int sizeX, final int sizeY, final int speed, final int y) {
    final int frameCount = spritesheet.getSheet().getWidth() / sizeX;
    final BufferedImage[] frames = new BufferedImage[frameCount];

    for (int i = 0; i < frameCount; i++)
      frames[i] = spritesheet.crop(i * sizeX, y * sizeY, sizeX, sizeY);

    return new Animation(frames, speed);
  }

  public String getName() {
    return name;
  }

  public Animation[][] getAnimations() {
    return animations;
  }

  public Animation getPortrait() {
    return portrait;
  }

  public Stats getStats() {
    return stats;
  }

  public Perks getPerks() {
    return perks;
  }
}
