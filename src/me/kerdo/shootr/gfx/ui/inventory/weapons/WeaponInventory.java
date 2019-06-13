package me.kerdo.shootr.gfx.ui.inventory.weapons;

import me.kerdo.shootr.Handler;
import me.kerdo.shootr.entity.creature.Player;
import me.kerdo.shootr.gfx.ui.UIObject;
import me.kerdo.shootr.weapon.Weapon;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;

public class WeaponInventory extends UIObject {
  private final Player player;

  private final List<UIObject> slots = new ArrayList<>();

  public WeaponInventory(final Handler handler, final int x, final int y, final int width, final int height, final Player player, final Weapon[] weapons) {
    super(handler, x, y, width, height);
    this.player = player;

    // visible = false;

    // Dynamically load weapon from inventory
    // Later add appear to mouse position

    slots.add(new WeaponInventorySlot(handler, x + width / 2 - WeaponInventorySlot.WIDTH / 2, y + WeaponInventorySlot.PADDING, WeaponInventorySlot.WIDTH, WeaponInventorySlot.HEIGHT, weapons[0]));
    slots.add(new WeaponInventorySlot(handler, x + width - (WeaponInventorySlot.WIDTH + WeaponInventorySlot.PADDING), y + height / 2 - WeaponInventorySlot.HEIGHT / 2, WeaponInventorySlot.WIDTH, WeaponInventorySlot.HEIGHT, weapons[1]));
    slots.add(new WeaponInventorySlot(handler, x + width / 2 - WeaponInventorySlot.WIDTH / 2, y + height - (WeaponInventorySlot.HEIGHT + WeaponInventorySlot.PADDING), WeaponInventorySlot.WIDTH, WeaponInventorySlot.HEIGHT, weapons[2]));
    slots.add(new WeaponInventorySlot(handler, x + WeaponInventorySlot.PADDING, y + height / 2 - WeaponInventorySlot.HEIGHT / 2, WeaponInventorySlot.WIDTH, WeaponInventorySlot.HEIGHT, weapons[3]));
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
    g.setColor(Color.WHITE);
    g.drawRect(x, y, width, height);
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
