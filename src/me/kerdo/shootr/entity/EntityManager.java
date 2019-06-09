package me.kerdo.shootr.entity;

import me.kerdo.shootr.Handler;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class EntityManager {
  private final Handler handler;

  private ArrayList<Entity> entities = new ArrayList<Entity>();
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
        if (isOutOfBounds(e)) continue;

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

        if (isOutOfBounds(e))
          continue;

        e.render(g);
        e.displayHealth(g);
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


  public void addEntity(Entity e) {
    entities.add(e);
  }

  public Handler getHandler() {
    return handler;
  }

  public ArrayList<Entity> getEntities() {
    return entities;
  }

  public void setEntities(ArrayList<Entity> entities) {
    this.entities = entities;
  }
}