package me.kerdo.shootr.entity.creature.player.panel.weapons;

import me.kerdo.shootr.Handler;
import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.gfx.Text;
import me.kerdo.shootr.gfx.ui.UIObject;
import me.kerdo.shootr.utils.UIFunctions;
import me.kerdo.shootr.weapon.Weapon;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class WeaponStatsPanel extends UIObject {
  private Weapon weapon;

  public WeaponStatsPanel(final Handler handler, final int x, final int y, final int width, final int height) {
    super(handler, x, y, width, height);
  }

  @Override
  public void tick(final double dt) {
  }

  @Override
  public void render(final Graphics g) {
    g.drawImage(Assets.controlPanelTabBackground[2], x, y, width, height, null);

    Text.drawString(g, weapon.getName(), x + 20, y + 20, true, Color.WHITE, Assets.andy16);
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

  public Weapon getWeapon() {
    return weapon;
  }

  public void setWeapon(final Weapon weapon) {
    this.weapon = weapon;
  }
}
