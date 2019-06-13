package me.kerdo.shootr.utils.save;

import me.kerdo.shootr.entity.character.Stamina;

public class SavePlayer {
  public double x, y;

  public int health, maxHealth;

  public int characterId;

  public Stamina stamina;

  public SaveWeapon[] weapons;
  public int selectedWeaponIndex;
  public int[] bullets;
}
