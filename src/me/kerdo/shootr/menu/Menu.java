package me.kerdo.shootr.menu;

import me.kerdo.shootr.Handler;

import java.awt.*;

public abstract class Menu {
  protected final Handler handler;

  public Menu(final Handler handler) {
    this.handler = handler;
  }

  public abstract void tick(final double dt);

  public abstract void render(final Graphics g);
}