package me.kerdo.shootr.weapon;

import me.kerdo.shootr.Handler;
import me.kerdo.shootr.entity.Entity;
import me.kerdo.shootr.world.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BulletManager {
  private final Handler handler;

  private final List<Bullet> bullets = new ArrayList<>();

  public BulletManager(final Handler handler) {
    this.handler = handler;
  }

  public void tick(final double dt) {
    final Iterator<Bullet> it = bullets.iterator();

    while (it.hasNext()) {
      final Bullet b = it.next();
      boolean remove = b.tick(dt);

      if (remove)
        it.remove();
    }

    collisions();
  }

  public void collisions() {
    final Iterator<Bullet> it = bullets.iterator();

    while (it.hasNext()) {
      final Bullet b = it.next();

      final double bx = b.getX(), by = b.getY();
      final int br = b.getSize();

      for (int i = 0; i < handler.getWorld().getEntityManager().getEntities().size(); i++) {
        final Entity e = handler.getWorld().getEntityManager().getEntities().get(i);

        if (e == handler.getWorld().getPlayer())
          continue;

        if (e.getY() + e.getWidth() < handler.getCamera().getyOff() || e.getY() > handler.getHeight() + handler.getCamera().getyOff() ||
                    e.getX() + e.getWidth() < handler.getCamera().getxOff() || e.getX() > handler.getWidth() + handler.getCamera().getxOff())
          continue;

        final Rectangle eb = new Rectangle((int) (e.getX()), (int) (e.getY()), (int) e.getWidth(),
                (int) e.getHeight());

        // TODO: MAX BULLET RANGE
        final Rectangle bb = new Rectangle((int) bx, (int) by, (int) br, (int) br);

        if (bb.intersects(eb)) {
          e.hurt((int) b.getDamage());
          it.remove();
          break;
        }
      }

      if (handler.getWorld().getTile((int) (bx / Tile.TILE_WIDTH), (int) (by / Tile.TILE_HEIGHT)).isSolid())
        it.remove();
    }
  }

  public void render(final Graphics g) {
    final Iterator<Bullet> it = bullets.iterator();
    while (it.hasNext()) {
      final Bullet b = it.next();

      b.render(g);
    }
  }

  public void addBullet(final Bullet bullet) {
    bullets.add(bullet);
  }
}
