package me.kerdo.shootr.menu;

import me.kerdo.shootr.Handler;
import me.kerdo.shootr.input.MouseManager;

public class MenuManager {
  private final Handler handler;
  private final MouseManager mouseManager;

  private Menu currentMenu = null;
  private Menu previousMenu = null;
  private boolean first = true;

  public MenuManager(final Handler handler, final MouseManager mouseManager) {
    this.handler = handler;
    this.mouseManager = mouseManager;
  }

  public void setMenu(final Menu menu) {
    if (first) {
      previousMenu = menu;
      currentMenu = menu;
      first = false;
    } else {
      previousMenu = currentMenu;
      currentMenu = menu;
    }

    mouseManager.setUiManager(menu.getUiManager());
  }

  public Menu getMenu() {
    return currentMenu;
  }

  public Menu getPreviousMenu() {
    return previousMenu;
  }

  public void setPreviousMenu(final Menu previousMenu) {
    this.previousMenu = previousMenu;
  }
}