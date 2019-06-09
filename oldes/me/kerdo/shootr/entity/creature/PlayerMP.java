package me.kerdo.shootr.entity.creature;

import java.awt.Color;
import java.awt.Graphics;

import me.kerdo.shootr.gfx.Animation;
import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.gfx.Text;
import me.kerdo.shootr.utils.handler.Handler;

public class PlayerMP extends Creature {

	private Animation anim;
	private String username;

	private final float DEFAULT_SPEED = 4f;

	public PlayerMP(Handler handler, float x, float y, String username) {
		super(handler, x, y, Creature.DEFAULT_WIDTH, Creature.DEFAULT_HEIGHT, 100);
		this.username = username;

		bounds.x = 16;
		bounds.y = 32;
		bounds.width = 32;
		bounds.height = 32;

		speed = DEFAULT_SPEED;

		anim = new Animation(55, Assets.player);
	}

	@Override
	public void tick() {
		anim.tick();
		move();
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(anim.getCurrentFrame(), (int) (x - handler.getCamera().getxOff()),
				(int) (y - handler.getCamera().getyOff()), width, height, null);
		
		Text.drawString(g, username, (int) (x + width / 2 - handler.getCamera().getxOff()), (int) (y - 15 - handler.getCamera().getyOff()), true, Color.WHITE, Assets.roboto16);
	}

	@Override
	public void die() {
		System.out.println("You lose!");
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}