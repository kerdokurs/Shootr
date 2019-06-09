package me.kerdo.shootr.entity.immovable;

import me.kerdo.shootr.Handler;
import me.kerdo.shootr.entity.Entity;

public abstract class ImmovableEntity extends Entity {
  public ImmovableEntity(final Handler handler, final float x, final float y, final int width, final int height, final int maxHealth) {
    super(handler, x, y, width, height, maxHealth);
  }
}
