package me.kerdo.shootr.menu;

import me.kerdo.shootr.Handler;
import me.kerdo.shootr.gfx.ui.UIManager;

import java.awt.*;

public abstract class Menu {
  protected final Handler handler;
  protected final UIManager uiManager;

  public Menu(final Handler handler) {
    this.handler = handler;

    this.uiManager = new UIManager(handler);
  }

  public abstract void tick(final double dt);

  public abstract void render(final Graphics g);

  public UIManager getUiManager() {
    return uiManager;
  }
}