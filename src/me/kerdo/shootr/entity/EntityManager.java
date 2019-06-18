package me.kerdo.shootr.entity;

import me.kerdo.shootr.Handler;
import me.kerdo.shootr.entity.creature.player.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

public class EntityManager {
  private final Handler handler;

  private final List<Entity> entities = new ArrayList<>();
  private Player player;

  private Comparator<Entity> comp = (final Entity a, final Entity b) -> {
    if (a.getY() + a.getHeight() < b.getY() + b.getHeight())
      return -1;
    else
      return 1;
  };

  public EntityManager(final Handler handler) {
    this.handler = handler;
  }

  public void tick(final double dt) {
    try {
      Iterator<Entity> it = entities.iterator();
      while (it.hasNext()) {
        Entity e = it.next();

        if (!(e instanceof Player) && isOutOfBounds(e)) continue;

        e.tick(dt);

        if (!e.isActive())
          it.remove();
      }

      entities.sort(comp);
    } catch (ConcurrentModificationException e) {
      return;
    }
  }

  public void render(Graphics g) {
    try {
      Iterator<Entity> it = entities.iterator();
      while (it.hasNext()) {
        Entity e = it.next();

        if (!(e instanceof Player) && isOutOfBounds(e))
          continue;

        e.render(g);
      }
    } catch (ConcurrentModificationException e) {
      return;
    }
  }

  private boolean isOutOfBounds(final Entity e) {
    return e.getY() + e.getWidth() < handler.getCamera().getyOff() ||
                   e.getY() > handler.getHeight() + handler.getCamera().getyOff() ||
                   e.getX() + e.getWidth() < handler.getCamera().getxOff() || e.getX() > handler.getWidth() + handler.getCamera().getxOff();
  }


  public void addEntity(final Entity e) {
    entities.add(e);
  }

  public Handler getHandler() {
    return handler;
  }

  public List<Entity> getEntities() {
    return entities;
  }

  public Player getPlayer() {
    return player;
  }

  public void setPlayer(final Player player) {
    this.player = player;
    addEntity(player);
  }
}