package me.kerdo.shootr.entity.character;

public class Stats {
  public static final int MELEE = 0, RANGED = 1, NINJA = 2;
  public static final String[] CLASSES = new String[] { "Melee", "Ranged", "Ninja" };

  public int health, speed, playClass;
  public Stamina stamina;
}
