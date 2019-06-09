package me.kerdo.shootr.menu;

import me.kerdo.shootr.Handler;
import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.gfx.Text;

import java.awt.*;

public class MainMenu extends Menu {
  public MainMenu(final Handler handler) {
    super(handler);
  }

  @Override
  public void tick(final double dt) {

  }

  @Override
  public void render(final Graphics g) {
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
    g.setColor(Color.WHITE);
    g.drawRect(handler.getWidth() / 2, handler.getHeight() / 2, 2, 2);
    Text.drawString(g, "Hello", handler.getWidth() / 2, handler.getHeight() / 2, false, Color.RED, Assets.andy32);
  }
}
