package me.kerdo.shootr.utils;

import me.kerdo.shootr.gfx.ui.UIObject;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;

public class UIFunctions {
  public static void mousePressed(final MouseEvent e, final List<UIObject> objects) {
    final int x = e.getX();
    final int y = e.getY();

    for (final UIObject object : new ArrayList<>(objects)) {
      if (object.isVisible()) {
        if (object.getBounds().contains(new Point(x, y))) {
          object.setHovering(true);
          object.mousePressed(e);
        } else
          object.setHovering(false);
      }
    }
  }

  public static void mouseReleased(final MouseEvent e, final List<UIObject> objects) {
    final int x = e.getX();
    final int y = e.getY();

    for (final UIObject object : new ArrayList<>(objects)) {
      if (object.isVisible()) {
        if (object.getBounds().contains(new Point(x, y))) {
          object.setHovering(true);
          object.mouseReleased(e);
        } else
          object.setHovering(false);
      }
    }
  }

  public static void mouseMoved(final MouseEvent e, final List<UIObject> objects) {
    final int x = e.getX();
    final int y = e.getY();

    for (final UIObject object : new ArrayList<>(objects)) {
      if (object.isVisible()) {
        if (object.getBounds().contains(new Point(x, y))) {
          object.setHovering(true);
          object.mouseMoved(e);
        } else
          object.setHovering(false);
      }
    }
  }

  public static void mouseWheelMoved(final MouseWheelEvent e, final List<UIObject> objects) {
    final int x = e.getX();
    final int y = e.getY();

    for (final UIObject object : new ArrayList<>(objects)) {
      if (object.isVisible()) {
        if (object.getBounds().contains(new Point(x, y))) {
          object.setHovering(true);
          object.mouseWheelMoved(e);
        } else
          object.setHovering(false);
      }
    }
  }

  public static void mouseDragged(final MouseEvent e, final List<UIObject> objects) {
    final int x = e.getX();
    final int y = e.getY();

    for (final UIObject object : new ArrayList<>(objects)) {
      if (object.isVisible()) {
        if (object.getBounds().contains(new Point(x, y))) {
          object.setHovering(true);
          object.mouseDragged(e);
        } else
          object.setHovering(false);
      }
    }
  }

  public static void mouseClicked(final MouseEvent e, final List<UIObject> objects) {
    final int x = e.getX();
    final int y = e.getY();

    for (final UIObject object : new ArrayList<>(objects)) {
      if (object.isVisible()) {
        if (object.getBounds().contains(new Point(x, y))) {
          object.setHovering(true);
          object.mouseClicked(e);
        } else
          object.setHovering(false);
      }
    }
  }

  public static void mouseEntered(final MouseEvent e, final List<UIObject> objects) {
    final int x = e.getX();
    final int y = e.getY();

    for (final UIObject object : new ArrayList<>(objects)) {
      if (object.isVisible()) {
        if (object.getBounds().contains(new Point(x, y))) {
          object.setHovering(true);
          object.mouseEntered(e);
        } else
          object.setHovering(false);
      }
    }
  }

  public static void mouseExited(final MouseEvent e, final List<UIObject> objects) {
    final int x = e.getX();
    final int y = e.getY();

    for (final UIObject object : new ArrayList<>(objects)) {
      if (object.isVisible()) {
        if (object.getBounds().contains(new Point(x, y))) {
          object.setHovering(true);
          object.mouseExited(e);
        } else
          object.setHovering(false);
      }
    }
  }
}
