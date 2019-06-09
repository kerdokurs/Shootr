package me.kerdo.shootr.entity.statik;

import java.awt.Graphics;

import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.tile.Tile;
import me.kerdo.shootr.utils.handler.Handler;

public class Post extends StaticEntity {
	public Post(Handler handler, float x, float y) {
		super(handler, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT * 2, 1000);
		
		bounds.x = 10;
		bounds.y = 20;
		bounds.width = width - 20;
		bounds.height = height - bounds.x;
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.post, (int) (x - handler.getCamera().getxOff()), (int) (y - handler.getCamera().getyOff()), width, height, null);
	}

	@Override
	public void die() {
		handler.getWorld().getEntityManager().getPlayer().getLevelBar().addXP(DEFAULT_XP_ON_DEATH * 1);
	}
}