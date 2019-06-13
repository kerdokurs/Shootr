package me.kerdo.shootr.gfx.ui.inventory.weapons;

import me.kerdo.shootr.Handler;
import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.gfx.ui.UIObject;
import me.kerdo.shootr.weapon.Weapon;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class WeaponInventorySlot extends UIObject {
  public static final int WIDTH = 128, HEIGHT = 64, PADDING = 10;

  private final Weapon weapon;

  public WeaponInventorySlot(final Handler handler, final int x, final int y, final int width, final int height, final Weapon weapon) {
    super(handler, x, y, width, height);
    this.weapon = weapon;
  }

  @Override
  public void tick(final double dt) {

  }

  @Override
  public void render(final Graphics g) {
    g.drawImage(Assets.weaponSlotBackground, this.x, this.y, 128, 64, null);

    if (weapon == null)
      return;

    int x = this.x, y = this.y;
    int width = this.width, height = this.height;

    int tWidth = weapon.getTextures()[0].getWidth();

    if (tWidth == 32) {
      x += 32;
      width /= 2;
    }

    if (hovering) {
      x -= (tWidth == 32) ? 3 : 6;
      y -= (tWidth == 32) ? 3 : 6;

      width += (tWidth == 32) ? 6 : 12;
      height += (tWidth == 32) ? 6 : 12;
    }

    g.drawImage(weapon.getTextures()[hovering ? 1 : 0], x, y, width, height, null);
  }

  @Override
  public void mousePressed(final MouseEvent e) {
  }

  @Override
  public void mouseReleased(final MouseEvent e) {
  }

  @Override
  public void mouseMoved(final MouseEvent e) {
  }

  @Override
  public void mouseWheelMoved(final MouseWheelEvent e) {
  }

  @Override
  public void mouseDragged(final MouseEvent e) {
  }

  @Override
  public void mouseClicked(final MouseEvent e) {
  }

  @Override
  public void mouseEntered(final MouseEvent e) {
  }

  @Override
  public void mouseExited(final MouseEvent e) {
  }
}
