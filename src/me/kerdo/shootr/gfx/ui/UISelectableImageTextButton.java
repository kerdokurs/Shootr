package me.kerdo.shootr.gfx.ui;

import me.kerdo.shootr.Handler;
import me.kerdo.shootr.gfx.Text;

import java.awt.*;
import java.awt.image.BufferedImage;

import static me.kerdo.shootr.gfx.ui.UISelectableImageButton.*;

public class UISelectableImageTextButton extends UIImageTextButton {
  private boolean selected = false;

  public UISelectableImageTextButton(final Handler handler, final int x, final int y, final int width, final int height, final UIClickable clickable, final BufferedImage[] frames, final String text, final Color color, final Font font) {
    super(handler, x, y, width, height, clickable, frames, text, color, font);
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

    Text.drawString(g, text, x + width / 2, y + height / 2, true, color, font);
  }

  public boolean isSelected() {
    return selected;
  }

  public void setSelected(final boolean selected) {
    this.selected = selected;
  }
}
