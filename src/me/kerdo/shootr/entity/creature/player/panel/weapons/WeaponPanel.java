package me.kerdo.shootr.entity.creature.player.panel.weapons;

import me.kerdo.shootr.Handler;
import me.kerdo.shootr.entity.creature.player.panel.ControlPanel;
import me.kerdo.shootr.gfx.ui.UIObject;
import me.kerdo.shootr.gfx.ui.UISelectableImageButton;
import me.kerdo.shootr.utils.UIFunctions;
import me.kerdo.shootr.weapon.Weapon;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class WeaponPanel extends UIObject {
  private final ControlPanel controlPanel;

  private final List<UIObject> weaponButtons = new ArrayList<>();
  private final List<UIObject> panels = new ArrayList<>();

  private final UIObject weaponStatsPanel;

  private int weaponIndex = 0;

  public WeaponPanel(final Handler handler, final int x, final int y, final int width, final int height, final ControlPanel controlPanel) {
    super(handler, x, y, width, height);
    this.controlPanel = controlPanel;

    visible = false;

    weaponStatsPanel = new WeaponStatsPanel(handler, x + 140, y, width - 140, height);
    panels.add(weaponStatsPanel);

    setWeapons(controlPanel.getPlayer().getWeapons());
    select(weaponIndex);
  }

  @Override
  public void tick(final double dt) {
    for (final UIObject object : weaponButtons) {
      if (object.isVisible())
        object.tick(dt);
    }

    for (final UIObject panel : panels) {
      if (panel.isVisible())
        panel.tick(dt);
    }
  }

  @Override
  public void render(final Graphics g) {
    for (final UIObject button : weaponButtons) {
      if (button.isVisible())
        button.render(g);
    }

    for (final UIObject panel : panels) {
      if (panel.isVisible())
        panel.render(g);
    }
  }

  public void setWeapons(final Weapon[] weapons) {
    weaponButtons.clear();

    for (int i = 0; i < weapons.length; i++) {
      final Weapon weapon = weapons[i];

      if (weapon == null)
        continue;

      weaponButtons.add(new UISelectableImageButton(handler, x, y + i * 64, 128, 64, object -> select(object), new BufferedImage[] { weapon.getTextures()[0], weapon.getTextures()[1], weapon.getTextures()[1] }));
    }
  }

  private void select(final UIObject object) {
    weaponIndex = getWeaponIndex(object);

    for (final UIObject button : weaponButtons)
      ((UISelectableImageButton) button).setSelected(false);
    ((UISelectableImageButton) object).setSelected(true);

    ((WeaponStatsPanel) weaponStatsPanel).setWeapon(controlPanel.getPlayer().getWeapons()[weaponIndex]);
  }

  public void select(final int id) {
    for (int i = 0; i < weaponButtons.size(); i++) {
      final UIObject object = weaponButtons.get(i);
      final UISelectableImageButton button = (UISelectableImageButton) object;

      if (i == id)
        select(button);
    }
  }

  private int getWeaponIndex(final UIObject imageButton) {
    return (int) (imageButton.getBounds().getY() - y) / 64;
  }

  @Override
  public void mousePressed(final MouseEvent e) {
    UIFunctions.mousePressed(e, weaponButtons);
  }

  @Override
  public void mouseReleased(final MouseEvent e) {
    UIFunctions.mouseReleased(e, weaponButtons);
  }

  @Override
  public void mouseMoved(final MouseEvent e) {
    UIFunctions.mouseMoved(e, weaponButtons);
  }

  @Override
  public void mouseWheelMoved(final MouseWheelEvent e) {
    UIFunctions.mouseWheelMoved(e, weaponButtons);
  }

  @Override
  public void mouseDragged(final MouseEvent e) {
    UIFunctions.mouseDragged(e, weaponButtons);
  }

  @Override
  public void mouseClicked(final MouseEvent e) {
    UIFunctions.mouseClicked(e, weaponButtons);
  }

  @Override
  public void mouseEntered(final MouseEvent e) {
    UIFunctions.mouseEntered(e, weaponButtons);
  }

  @Override
  public void mouseExited(final MouseEvent e) {
    UIFunctions.mouseExited(e, weaponButtons);
  }
}
