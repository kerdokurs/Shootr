package me.kerdo.shootr.entity.creature.player.panel;

import me.kerdo.shootr.Handler;
import me.kerdo.shootr.entity.creature.player.Player;
import me.kerdo.shootr.entity.creature.player.inventory.Inventory;
import me.kerdo.shootr.entity.creature.player.panel.weapons.WeaponPanel;
import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.gfx.ui.UIObject;
import me.kerdo.shootr.gfx.ui.UISelectableImageTextButton;
import me.kerdo.shootr.utils.UIFunctions;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;

public class ControlPanel extends UIObject {
  public static final int WIDTH = 400, HEIGHT = 400;

  private final List<UIObject> tabs = new ArrayList<>();
  private final List<UIObject> panels = new ArrayList<>();
  private final Player player;
  private int tabIndex = 0;
  private final String[] tabNames = new String[] { "Inventory", "Weapons", "Character", "Map" };

  public ControlPanel(final Handler handler, final int x, final int y, final int width, final int height, final Player player) {
    super(handler, x, y, width, height);
    this.player = player;

    final int tabWidth = width / tabNames.length;

    for (int i = 0; i < tabNames.length; i++) {
      final String tabName = tabNames[i];

      final UIObject tabButton = new UISelectableImageTextButton(handler, x + i * tabWidth, y, tabWidth, 25, object -> select(object), Assets.controlPanelTabBackground, tabName, Color.WHITE, Assets.andy16);

      tabs.add(tabButton);
    }

    final Inventory inventory = new Inventory(handler, x, y + 25, width, height - 25, this);
    panels.add(inventory);

    final WeaponPanel weaponPanel = new WeaponPanel(handler, x, y + 25, width, height - 25, this);
    panels.add(weaponPanel);

    select(0);
  }

  @Override
  public void tick(final double dt) {
    for (final UIObject object : tabs) {
      if (object.isVisible())
        object.tick(dt);
    }

    if (panels.size() > tabIndex)
      if (panels.get(tabIndex) != null)
        panels.get(tabIndex).tick(dt);
  }

  @Override
  public void render(final Graphics g) {
    g.drawImage(Assets.controlPanelBackground, x, y, width, height, null);

    for (final UIObject object : tabs) {
      if (object.isVisible())
        object.render(g);
    }

    if (panels.size() > tabIndex)
      if (panels.get(tabIndex) != null)
        panels.get(tabIndex).render(g);
  }

  @Override
  public void mousePressed(final MouseEvent e) {
    UIFunctions.mousePressed(e, tabs);

    if (panels.size() > tabIndex)
      if (panels.get(tabIndex) != null)
        panels.get(tabIndex).mousePressed(e);
  }

  @Override
  public void mouseReleased(final MouseEvent e) {
    UIFunctions.mouseReleased(e, tabs);

    if (panels.size() > tabIndex)
      if (panels.get(tabIndex) != null)
        panels.get(tabIndex).mouseReleased(e);
  }

  @Override
  public void mouseMoved(final MouseEvent e) {
    UIFunctions.mouseMoved(e, tabs);

    if (panels.size() > tabIndex)
      if (panels.get(tabIndex) != null)
        panels.get(tabIndex).mouseMoved(e);
  }

  @Override
  public void mouseWheelMoved(final MouseWheelEvent e) {
    UIFunctions.mouseWheelMoved(e, tabs);

    if (panels.size() > tabIndex)
      if (panels.get(tabIndex) != null)
        panels.get(tabIndex).mouseWheelMoved(e);
  }

  @Override
  public void mouseDragged(final MouseEvent e) {
    UIFunctions.mouseDragged(e, tabs);

    if (panels.size() > tabIndex)
      if (panels.get(tabIndex) != null)
        panels.get(tabIndex).mouseDragged(e);
  }

  @Override
  public void mouseClicked(final MouseEvent e) {
    UIFunctions.mouseClicked(e, tabs);

    if (panels.size() > tabIndex)
      if (panels.get(tabIndex) != null)
        panels.get(tabIndex).mouseClicked(e);
  }

  @Override
  public void mouseEntered(final MouseEvent e) {
    UIFunctions.mouseEntered(e, tabs);

    if (panels.size() > tabIndex)
      if (panels.get(tabIndex) != null)
        panels.get(tabIndex).mouseEntered(e);
  }

  @Override
  public void mouseExited(final MouseEvent e) {
    UIFunctions.mouseExited(e, tabs);

    if (panels.size() > tabIndex)
      if (panels.get(tabIndex) != null)
        panels.get(tabIndex).mouseExited(e);
  }

  public int getTabIndex(final String tabName) {
    for (int i = 0; i < tabNames.length; i++) {
      final String s = tabNames[i];

      if (s.equalsIgnoreCase(tabName))
        return i;
    }

    return -1;
  }

  public void select(final UIObject tab) {
    tabIndex = getTabIndex(((UISelectableImageTextButton) tab).getText());

    for (final UIObject object : tabs)
      ((UISelectableImageTextButton) object).setSelected(false);
    ((UISelectableImageTextButton) tab).setSelected(true);
  }

  public void select(final int id) {
    tabIndex = id;

    for (final UIObject object : tabs)
      ((UISelectableImageTextButton) object).setSelected(false);

    for (int i = 0; i < tabs.size(); i++) {
      final UIObject object = tabs.get(i);
      final UISelectableImageTextButton tab = (UISelectableImageTextButton) object;

      if (i == id)
        tab.setSelected(true);
      else
        tab.setSelected(false);
    }
  }

  public Player getPlayer() {
    return player;
  }
}
