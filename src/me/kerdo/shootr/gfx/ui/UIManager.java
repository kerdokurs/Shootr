package me.kerdo.shootr.gfx.ui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.List;
import java.util.ArrayList;

public class UIManager {
  private final List<UIObject> objects = new ArrayList<UIObject>();

  public void tick(final double dt) {
    for (final UIObject object : objects) {
      if (object.isVisible())
        object.tick(dt);
    }
  }

  public void render(final Graphics g) {
    for (final UIObject object : objects) {
      if (object.isVisible())
        object.render(g);
    }
  }

  public void mousePressed(final MouseEvent e) {
    final int x = e.getX();
    final int y = e.getY();

    for (final UIObject object : objects) {
      if (object.isVisible()) {
        if (object.getBounds().contains(new Point(x, y))) {
          object.setHovering(true);
          object.mousePressed(e);
        } else
          object.setHovering(false);
      }
    }
  }

  public void mouseReleased(final MouseEvent e) {
    final int x = e.getX();
    final int y = e.getY();

    for (final UIObject object : objects) {
      if (object.isVisible()) {
        if (object.getBounds().contains(new Point(x, y))) {
          object.setHovering(true);
          object.mouseReleased(e);
        } else
          object.setHovering(false);
      }
    }
  }

  public void mouseMoved(final MouseEvent e) {
    final int x = e.getX();
    final int y = e.getY();

    for (final UIObject object : objects) {
      if (object.isVisible()) {
        if (object.getBounds().contains(new Point(x, y))) {
          object.setHovering(true);
          object.mouseMoved(e);
        } else
          object.setHovering(false);
      }
    }
  }

  public void mouseWheelMoved(final MouseWheelEvent e) {
    final int x = e.getX();
    final int y = e.getY();

    for (final UIObject object : objects) {
      if (object.isVisible()) {
        if (object.getBounds().contains(new Point(x, y))) {
          object.setHovering(true);
          object.mouseWheelMoved(e);
        } else
          object.setHovering(false);
      }
    }
  }

  public void mouseDragged(final MouseEvent e) {
    final int x = e.getX();
    final int y = e.getY();

    for (final UIObject object : objects) {
      if (object.isVisible()) {
        if (object.getBounds().contains(new Point(x, y))) {
          object.setHovering(true);
          object.mouseDragged(e);
        } else
          object.setHovering(false);
      }
    }
  }

  public void mouseClicked(final MouseEvent e) {
    final int x = e.getX();
    final int y = e.getY();

    for (final UIObject object : objects) {
      if (object.isVisible()) {
        if (object.getBounds().contains(new Point(x, y))) {
          object.setHovering(true);
          object.mouseClicked(e);
        } else
          object.setHovering(false);
      }
    }
  }

  public void mouseEntered(final MouseEvent e) {
    final int x = e.getX();
    final int y = e.getY();

    for (final UIObject object : objects) {
      if (object.isVisible()) {
        if (object.getBounds().contains(new Point(x, y))) {
          object.setHovering(true);
          object.mousePressed(e);
        } else
          object.setHovering(false);
      }
    }
  }

  public void mouseExited(final MouseEvent e) {
    final int x = e.getX();
    final int y = e.getY();

    for (final UIObject object : objects) {
      if (object.isVisible()) {
        if (object.getBounds().contains(new Point(x, y))) {
          object.setHovering(true);
          object.mouseExited(e);
        } else
          object.setHovering(false);
      }
    }
  }

  public void addObject(final UIObject object) {
    objects.add(object);
  }

  public void removeObject(final UIObject object) {
    objects.remove(object);
  }
}
