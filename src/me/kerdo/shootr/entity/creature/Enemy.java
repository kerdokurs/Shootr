package me.kerdo.shootr.entity.creature;

import me.kerdo.shootr.Handler;
import me.kerdo.shootr.entity.Entity;
import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.gfx.Text;

import java.awt.*;

public class Enemy extends Entity {
  public Enemy(final Handler handler, final float x, final float y, final int width, final int height, final int maxHealth) {
    super(handler, x, y, width, height, maxHealth);
  }

  @Override
  public void tick(final double dt) {
  }

  @Override
  public void render(final Graphics g) {
    g.drawImage(Assets.cursor, (int) (x - handler.getCamera().getxOff()), (int) (y - handler.getCamera().getyOff()), width, height, null);

    Text.drawString(g, health + "/" + maxHealth, (int) ((x + width / 2) - handler.getCamera().getxOff()), (int) ((y - 20) - handler.getCamera().getyOff()), true, Color.WHITE, Assets.andy16);
  }

  @Override
  public void die() {
  }
}
