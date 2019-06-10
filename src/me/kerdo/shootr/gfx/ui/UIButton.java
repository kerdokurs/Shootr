package me.kerdo.shootr.gfx.ui;

import me.kerdo.shootr.Handler;

import java.awt.event.MouseEvent;

public abstract class UIButton extends UIObject {
  private final UIClickable clickable;

  public UIButton(final Handler handler, final int x, final int y, final int width, final int height, final UIClickable clickable) {
    super(handler, x, y, width, height);

    this.clickable = clickable;
  }

  @Override
  public void mouseClicked(final MouseEvent e) {
    clickable.click(this);
  }
}
