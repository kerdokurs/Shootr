package me.kerdo.shootr.entity.statik;

import java.awt.Graphics;

import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.items.Item;
import me.kerdo.shootr.tile.Tile;
import me.kerdo.shootr.utils.handler.Handler;

public class Tree extends StaticEntity {
	public Tree(Handler handler, float x, float y) {
		super(handler, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT * 2, 250);
		
		maxHealth = 250;
		
		bounds.x = 10;
		bounds.y = (int) (height / 1.5f);
		bounds.width = width - 20;
		bounds.height = (int) (height - height / 1.5f);
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.tree, (int) (x - handler.getCamera().getxOff()), (int) (y - handler.getCamera().getyOff()), width, height, null);
	}

	@Override
	public void die() {
		handler.getWorld().getItemManager().addItem(Item.wood.createNew((int) (x + Tile.TILE_WIDTH / 2) - Item.ITEM_WIDTH / 2,
												   (int) (y + Tile.TILE_HEIGHT) - Item.ITEM_WIDTH / 2));
		handler.getWorld().getEntityManager().getPlayer().getLevelBar().addXP(DEFAULT_XP_ON_DEATH * 3);
	}
}