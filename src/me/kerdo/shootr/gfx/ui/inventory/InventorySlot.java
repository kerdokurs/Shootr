package me.kerdo.shootr.gfx.ui.inventory;

import me.kerdo.shootr.Handler;
import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.gfx.Text;
import me.kerdo.shootr.gfx.ui.UIClickable;
import me.kerdo.shootr.gfx.ui.UIImageButton;
import me.kerdo.shootr.item.Item;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class InventorySlot extends UIImageButton {
  public static int WIDTH = 48, HEIGHT = 48;
  public static int PADDING = 3;
  private final Inventory inventory;
  private Item item;

  public InventorySlot(final Handler handler, final Inventory inventory, final int x, final int y, final int width, final int height, final UIClickable clickable, final BufferedImage texture) {
    super(handler, x, y, width, height, clickable, new BufferedImage[] { texture }, "");
    this.inventory = inventory;
  }

  @Override
  public void render(final Graphics g) {
    g.drawImage(frames[0], x, y, width, height, null);

    if (item != null) {
      final int x = (width - Item.WIDTH) / 2, y = (height - Item.HEIGHT) / 2;

      g.drawImage(item.getTexture(hovering), this.x + x, this.y + y, Item.WIDTH, Item.HEIGHT, null);
    }
  }

  public void postRender(final Graphics g) {
    if (hovering) {
      if (item != null) {
        int width = Inventory.WIDTH, height = Inventory.HEIGHT;
        int x = handler.getWidth() - Inventory.WIDTH - 30 - width, y = handler.getHeight() - 20 - height;

        g.drawImage(Assets.itemDescriptionBackground, x, y, width, height, null);

        Text.drawString(g, item.getName(), x + width / 2, y + 32, true, Color.WHITE, Assets.andy24);
        Text.drawString(g, item.getDescription(), x + width / 2, y + 56, true, Color.WHITE, Assets.andy16);
      }
    }
  }

  public Item getItem() {
    return item;
  }

  public void setItem(final Item item) {
    this.item = item;
  }

  @Override
  public void mouseClicked(final MouseEvent e) {
  }
}
