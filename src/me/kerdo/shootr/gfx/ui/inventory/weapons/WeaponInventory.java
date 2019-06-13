package me.kerdo.shootr.gfx.ui.inventory.weapons;

import me.kerdo.shootr.Handler;
import me.kerdo.shootr.entity.creature.Player;
import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.gfx.ui.UIObject;
import me.kerdo.shootr.weapons.Weapon;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class WeaponInventory extends UIObject {
  private final Player player;

  int p = 64;

  private final List<UIObject> slots = new ArrayList<>();

  public WeaponInventory(final Handler handler, final int x, final int y, final int width, final int height, final Player player, final Weapon[] weapons) {
    super(handler, x, y, width, height);
    this.player = player;

    // visible = false;

    // Dynamically load weapons from inventory
    // Later add appear to mouse position

    slots.add(new WeaponInventorySlot(handler, x + 74, y + 10, 128, 64, weapons[0].getTexture()));
    slots.add(new WeaponInventorySlot(handler, x + width / 2 + p, y + 106, 128, 64, weapons[1].getTexture()));
    slots.add(new WeaponInventorySlot(handler, x + 67, y + height / 2 + p, 128, 64, weapons[2].getTexture()));
    slots.add(new WeaponInventorySlot(handler, x - p + 10, y + 106, 128, 64, weapons[3].getTexture()));
  }

  @Override
  public void tick(final double dt) {
    if (!hovering) {
      for (final UIObject object : slots)
        object.setHovering(false);
    }
  }

  @Override
  public void render(final Graphics g) {
    for (final UIObject object : slots) {
      object.render(g);
    }
  }

  @Override
  public void mousePressed(final MouseEvent e) {
    final int x = e.getX();
    final int y = e.getY();

    for (final UIObject slot : slots) {
      if (slot.isVisible()) {
        if (slot.getBounds().contains(new Point(x, y))) {
          slot.setHovering(true);
          slot.mousePressed(e);
        } else
          slot.setHovering(false);
      }
    }
  }

  @Override
  public void mouseReleased(final MouseEvent e) {
    final int x = e.getX();
    final int y = e.getY();

    for (final UIObject slot : slots) {
      if (slot.isVisible()) {
        if (slot.getBounds().contains(new Point(x, y))) {
          slot.setHovering(true);
          slot.mouseReleased(e);
        } else
          slot.setHovering(false);
      }
    }
  }

  @Override
  public void mouseMoved(final MouseEvent e) {
    final int x = e.getX();
    final int y = e.getY();

    for (final UIObject slot : slots) {
      if (slot.isVisible()) {
        if (slot.getBounds().contains(new Point(x, y))) {
          slot.setHovering(true);
          slot.mouseMoved(e);
        } else
          slot.setHovering(false);
      }
    }
  }

  @Override
  public void mouseWheelMoved(final MouseWheelEvent e) {
    final int x = e.getX();
    final int y = e.getY();

    for (final UIObject slot : slots) {
      if (slot.isVisible()) {
        if (slot.getBounds().contains(new Point(x, y))) {
          slot.setHovering(true);
          slot.mouseWheelMoved(e);
        } else
          slot.setHovering(false);
      }
    }
  }

  @Override
  public void mouseDragged(final MouseEvent e) {
    final int x = e.getX();
    final int y = e.getY();

    for (final UIObject slot : slots) {
      if (slot.isVisible()) {
        if (slot.getBounds().contains(new Point(x, y))) {
          slot.setHovering(true);
          slot.mouseDragged(e);
        } else
          slot.setHovering(false);
      }
    }
  }

  @Override
  public void mouseClicked(final MouseEvent e) {
    final int x = e.getX();
    final int y = e.getY();

    for (final UIObject slot : slots) {
      if (slot.isVisible()) {
        if (slot.getBounds().contains(new Point(x, y))) {
          slot.setHovering(true);
          slot.mouseClicked(e);
        } else
          slot.setHovering(false);
      }
    }
  }

  @Override
  public void mouseEntered(final MouseEvent e) {
    final int x = e.getX();
    final int y = e.getY();

    for (final UIObject slot : slots) {
      if (slot.isVisible()) {
        if (slot.getBounds().contains(new Point(x, y))) {
          slot.setHovering(true);
          slot.mouseEntered(e);
        } else
          slot.setHovering(false);
      }
    }
  }

  @Override
  public void mouseExited(final MouseEvent e) {
    final int x = e.getX();
    final int y = e.getY();

    for (final UIObject slot : slots) {
      if (slot.isVisible()) {
        if (slot.getBounds().contains(new Point(x, y))) {
          slot.setHovering(true);
          slot.mouseExited(e);
        } else
          slot.setHovering(false);
      }
    }
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }
}
