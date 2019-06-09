package me.kerdo.shootr.menu;

import me.kerdo.shootr.Handler;

public class MenuManager {
  private Menu currentMenu = null;
  private Menu previousMenu = null;
  private boolean first = true;

  public void setMenu(final Menu menu) {
    if (first) {
      previousMenu = menu;
      currentMenu = menu;
      first = false;
    } else {
      previousMenu = currentMenu;
      currentMenu = menu;
    }
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