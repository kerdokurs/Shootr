package me.kerdo.shootr.gfx.ui;

import me.kerdo.shootr.Handler;
import me.kerdo.shootr.utils.UIFunctions;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;

public class UIManager {
  private final Handler handler;

  private final List<UIObject> objects = new ArrayList<UIObject>();

  public UIManager(final Handler handler) {
    this.handler = handler;
  }

  public void tick(final double dt) {
    for (final UIObject object : new ArrayList<>(objects)) {
      if (object.isVisible())
        object.tick(dt);
    }
  }

  public void render(final Graphics g) {
    for (final UIObject object : new ArrayList<>(objects)) {
      if (object.isVisible())
        object.render(g);
    }
  }

  public void mousePressed(final MouseEvent e) {
    /*final int x = e.getX();
    final int y = e.getY();

    for (final UIObject object : new ArrayList<>(objects)) {
      if (object.isVisible()) {
        if (object.getBounds().contains(new Point(x, y))) {
          object.setHovering(true);
          object.mousePressed(e);
        } else
          object.setHovering(false);
      }
    }*/

    UIFunctions.mousePressed(e, objects);

    if (handler.getWorld() != null)
      if (handler.getWorld().getEntityManager() != null)
        if (handler.getWorld().getEntityManager().getPlayer() != null)
          handler.getWorld().getEntityManager().getPlayer().mousePressed(e);
  }

  public void mouseReleased(final MouseEvent e) {
    /*final int x = e.getX();
    final int y = e.getY();

    for (final UIObject object : new ArrayList<>(objects)) {
      if (object.isVisible()) {
        if (object.getBounds().contains(new Point(x, y))) {
          object.setHovering(true);
          object.mouseReleased(e);
        } else
          object.setHovering(false);
      }
    }*/

    UIFunctions.mouseReleased(e, objects);

    if (handler.getWorld() != null)
      if (handler.getWorld().getEntityManager() != null)
        if (handler.getWorld().getEntityManager().getPlayer() != null)
          handler.getWorld().getEntityManager().getPlayer().mouseReleased(e);
  }

  public void mouseMoved(final MouseEvent e) {
    /*final int x = e.getX();
    final int y = e.getY();

    for (final UIObject object : new ArrayList<>(objects)) {
      if (object.isVisible()) {
        if (object.getBounds().contains(new Point(x, y))) {
          object.setHovering(true);
          object.mouseMoved(e);
        } else
          object.setHovering(false);
      }
    }*/

    UIFunctions.mouseMoved(e, objects);

    if (handler.getWorld() != null)
      if (handler.getWorld().getEntityManager() != null)
        if (handler.getWorld().getEntityManager().getPlayer() != null)
          handler.getWorld().getEntityManager().getPlayer().mouseMoved(e);
  }

  public void mouseWheelMoved(final MouseWheelEvent e) {
    /*final int x = e.getX();
    final int y = e.getY();

    for (final UIObject object : new ArrayList<>(objects)) {
      if (object.isVisible()) {
        if (object.getBounds().contains(new Point(x, y))) {
          object.setHovering(true);
          object.mouseWheelMoved(e);
        } else
          object.setHovering(false);
      }
    }*/

    UIFunctions.mouseWheelMoved(e, objects);

    if (handler.getWorld() != null)
      if (handler.getWorld().getEntityManager() != null)
        if (handler.getWorld().getEntityManager().getPlayer() != null)
          handler.getWorld().getEntityManager().getPlayer().mouseWheelMoved(e);
  }

  public void mouseDragged(final MouseEvent e) {
    /*final int x = e.getX();
    final int y = e.getY();

    for (final UIObject object : new ArrayList<>(objects)) {
      if (object.isVisible()) {
        if (object.getBounds().contains(new Point(x, y))) {
          object.setHovering(true);
          object.mouseDragged(e);
        } else
          object.setHovering(false);
      }
    }*/

    UIFunctions.mouseDragged(e, objects);

    if (handler.getWorld() != null)
      if (handler.getWorld().getEntityManager() != null)
        if (handler.getWorld().getEntityManager().getPlayer() != null)
          handler.getWorld().getEntityManager().getPlayer().mouseDragged(e);
  }

  public void mouseClicked(final MouseEvent e) {
    /*final int x = e.getX();
    final int y = e.getY();

    for (final UIObject object : new ArrayList<>(objects)) {
      if (object.isVisible()) {
        if (object.getBounds().contains(new Point(x, y))) {
          object.setHovering(true);
          object.mouseClicked(e);
        } else
          object.setHovering(false);
      }
    }*/

    UIFunctions.mouseClicked(e, objects);

    if (handler.getWorld() != null)
      if (handler.getWorld().getEntityManager() != null)
        if (handler.getWorld().getEntityManager().getPlayer() != null)
          handler.getWorld().getEntityManager().getPlayer().mouseClicked(e);
  }

  public void mouseEntered(final MouseEvent e) {
    /*final int x = e.getX();
    final int y = e.getY();

    for (final UIObject object : new ArrayList<>(objects)) {
      if (object.isVisible()) {
        if (object.getBounds().contains(new Point(x, y))) {
          object.setHovering(true);
          object.mouseEntered(e);
        } else
          object.setHovering(false);
      }
    }*/

    UIFunctions.mouseEntered(e, objects);

    if (handler.getWorld() != null)
      if (handler.getWorld().getEntityManager() != null)
        if (handler.getWorld().getEntityManager().getPlayer() != null)
          handler.getWorld().getEntityManager().getPlayer().mouseEntered(e);
  }

  public void mouseExited(final MouseEvent e) {
    /*final int x = e.getX();
    final int y = e.getY();

    for (final UIObject object : new ArrayList<>(objects)) {
      if (object.isVisible()) {
        if (object.getBounds().contains(new Point(x, y))) {
          object.setHovering(true);
          object.mouseExited(e);
        } else
          object.setHovering(false);
      }
    }*/

    UIFunctions.mouseExited(e, objects);

    if (handler.getWorld() != null)
      if (handler.getWorld().getEntityManager() != null)
        if (handler.getWorld().getEntityManager().getPlayer() != null)
          handler.getWorld().getEntityManager().getPlayer().mouseExited(e);
  }

  public void addObject(final UIObject object) {
    objects.add(object);
  }

  public void removeObject(final UIObject object) {
    objects.remove(object);
  }
}
