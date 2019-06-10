package me.kerdo.shootr.gfx.ui.inventory;

import me.kerdo.shootr.Handler;
import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.gfx.ui.UIObject;
import me.kerdo.shootr.item.Item;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;

public class Inventory extends UIObject {
  public static int ROWS = 6, COLS = 5;
  public static int WIDTH = 2 * InventorySlot.PADDING + InventorySlot.WIDTH * COLS + (COLS - 1) * InventorySlot.PADDING,
          HEIGHT = 2 * InventorySlot.PADDING + InventorySlot.HEIGHT * ROWS + (ROWS - 1) * InventorySlot.PADDING;

  private List<InventorySlot> slots = new ArrayList<>();

  public Inventory(final Handler handler, final int x, final int y, final int width, final int height) {
    super(handler, x, y, width, height);

    for (int _y = 0; _y < ROWS; _y++)
      for (int _x = 0; _x < COLS; _x++)
        slots.add(new InventorySlot(handler, this, x + InventorySlot.PADDING + _x * InventorySlot.WIDTH + InventorySlot.PADDING * _x, y + InventorySlot.PADDING + _y * InventorySlot.HEIGHT + InventorySlot.PADDING * _y, InventorySlot.WIDTH, InventorySlot.HEIGHT, slot -> {
        }, Assets.inventorySlotBackground));

    slots.get(0).setItem(Item.ITEMS[0]);
  }

  @Override
  public void tick(final double dt) {
  }

  @Override
  public void render(final Graphics g) {
    g.drawImage(Assets.inventoryBackground, x, y, width, height, null);

    for (final InventorySlot slot : slots)
      slot.render(g);

    for (final InventorySlot slot : slots)
      slot.postRender(g);
  }

  @Override
  public void mousePressed(final MouseEvent e) {
    final int x = e.getX();
    final int y = e.getY();

    for (final InventorySlot slot : slots) {
      if (slot.isVisible()) {
        if (slot.getBounds().contains(new Point(x, y)))
          slot.mousePressed(e);
      }
    }
  }

  @Override
  public void mouseReleased(final MouseEvent e) {
    final int x = e.getX();
    final int y = e.getY();

    for (final InventorySlot slot : slots) {
      if (slot.isVisible()) {
        if (slot.getBounds().contains(new Point(x, y)))
          slot.mouseReleased(e);
      }
    }
  }

  @Override
  public void mouseMoved(final MouseEvent e) {
    final int x = e.getX();
    final int y = e.getY();

    for (final InventorySlot slot : slots) {
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

    for (final InventorySlot slot : slots) {
      if (slot.isVisible()) {
        if (slot.getBounds().contains(new Point(x, y)))
          slot.mouseWheelMoved(e);
      }
    }
  }

  @Override
  public void mouseDragged(final MouseEvent e) {
    final int x = e.getX();
    final int y = e.getY();

    for (final InventorySlot slot : slots) {
      if (slot.isVisible()) {
        if (slot.getBounds().contains(new Point(x, y)))
          slot.mouseDragged(e);
      }
    }
  }

  @Override
  public void mouseClicked(final MouseEvent e) {
    final int x = e.getX();
    final int y = e.getY();

    for (final InventorySlot slot : slots) {
      if (slot.isVisible()) {
        if (slot.getBounds().contains(new Point(x, y)))
          slot.mouseClicked(e);
      }
    }
  }

  @Override
  public void mouseEntered(final MouseEvent e) {
    final int x = e.getX();
    final int y = e.getY();

    for (final InventorySlot slot : slots) {
      if (slot.isVisible()) {
        if (slot.getBounds().contains(new Point(x, y)))
          slot.mouseEntered(e);
      }
    }
  }

  public void mouseExited(final MouseEvent e) {
    final int x = e.getX();
    final int y = e.getY();

    for (final InventorySlot slot : slots) {
      if (slot.isVisible()) {
        if (slot.getBounds().contains(new Point(x, y)))
          slot.mouseExited(e);
      }
    }
  }

  @Override
  public void setVisible(boolean visible) {
    super.setVisible(visible);

    for (final InventorySlot slot : slots)
      slot.setHovering(false);
  }
}
