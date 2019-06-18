package me.kerdo.shootr.gfx.ui;

import me.kerdo.shootr.Handler;
import me.kerdo.shootr.gfx.Text;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UISelectableImageButton extends UIImageButton {
  private boolean selected = false;

  public static final int NOT_HOVERING_NOT_SELECTED = 0, HOVERING_NOT_SELECTED = 1, NOT_HOVERING_SELECTED = 1, HOVERING_SELECTED = 2;

  public UISelectableImageButton(Handler handler, int x, int y, int width, int height, UIClickable clickable, BufferedImage[] frames) {
    super(handler, x, y, width, height, clickable, frames);
  }

  @Override
  public void render(final Graphics g) {
    if (hovering) {
      if (selected)
        g.drawImage(frames[HOVERING_SELECTED], x, y, width, height, null);
      else
        g.drawImage(frames[HOVERING_NOT_SELECTED], x, y, width, height, null);
    } else {
      if (selected)
        g.drawImage(frames[NOT_HOVERING_SELECTED], x, y, width, height, null);
      else
        g.drawImage(frames[NOT_HOVERING_NOT_SELECTED], x, y, width, height, null);
    }
  }

  public boolean isSelected() {
    return selected;
  }

  public void setSelected(final boolean selected) {
    this.selected = selected;
  }
}
