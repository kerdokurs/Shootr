package me.kerdo.shootr.entity.creature;

import java.awt.Graphics;

import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.utils.handler.Handler;

public class Enemy extends Creature {
	private Handler handler;
	private Player player;

	private float moveSpeed = 50;

	private int dmgTimer = 0, dmgDelay = 30;
	
	private int attackRadius = 48;

	public Enemy(Handler handler, float x, float y) {
		super(handler, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT, 500);
		this.handler = handler;
	}

	@Override
	public void tick() {
		move();

		player = handler.getWorld().getEntityManager().getPlayer();

		double distance = Math.hypot(x - player.getX(), y - player.getY());

		if(distance <= 250) {
			xMove = (player.getX() - x) / moveSpeed;
			yMove = (player.getY() - y) / moveSpeed;
		} else {
			xMove = 0;
			yMove = 0;
		}

		if(dmgTimer <= dmgDelay)
			dmgTimer++;
		if(distance <= attackRadius) {
			if(dmgTimer >= dmgDelay) {
				player.hurt(25);
				dmgTimer = 0;
			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.enemy, (int) (x - handler.getCamera().getxOff()), (int) (y - handler.getCamera().getyOff()),
				width, height, null);
	}

	@Override
	public void die() {
		handler.getWorld().getEntityManager().getPlayer().getLevelBar().addXP(DEFAULT_XP_ON_DEATH * 4);
		handler.getWorld().getEntityManager().getPlayer().addCoins(1);
	}
}