package me.kerdo.shootr.entity.statik;

import me.kerdo.shootr.entity.Entity;
import me.kerdo.shootr.utils.handler.Handler;

public abstract class StaticEntity extends Entity {
	public StaticEntity(Handler handler, float x, float y, int width, int height, int maxHealth) {
		super(handler, x, y, width, height, maxHealth);
	}
}