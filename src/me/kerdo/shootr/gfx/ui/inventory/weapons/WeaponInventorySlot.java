package me.kerdo.shootr.gfx.ui.inventory.weapons;

import me.kerdo.shootr.Handler;
import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.gfx.ui.UIClickable;
import me.kerdo.shootr.gfx.ui.UIImage;
import me.kerdo.shootr.item.Item;

import java.awt.*;
import java.awt.image.BufferedImage;

public class WeaponInventorySlot extends UIImage {
  public WeaponInventorySlot(final Handler handler, final int x, final int y, final int width, final int height, final BufferedImage texture) {
    super(handler, x, y, width, height, texture, false);
  }

  @Override
  public void render(Graphics g) {
    int x = this.x, y = this.y;
    int width = this.width, height = this.height;

    if (hovering) {
      x -= 6;
      y -= 6;
      width += 6;
      height += 6;
    }

    g.drawImage(Assets.inventorySlotBackground, this.x - 6, this.y - 6, 140, 72, null);
    g.drawImage(texture, x, y, width, height, null);
  }
}
