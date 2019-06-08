package me.kerdo.shootr.portal;

import java.awt.Graphics;
import java.awt.Rectangle;

import me.kerdo.shootr.entity.Entity;
import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.utils.handler.Handler;

public class Portal {
	private Handler handler;
	private String name;
	private int x, y, width, height;

	private Rectangle bounds;

	public Portal(Handler handler, String name, int x, int y) {
		this.handler = handler;
		this.name = name;
		this.x = x;
		this.y = y;
		width = 40;
		height = 64;
		bounds = new Rectangle();

		bounds.x = x;
		bounds.y = y;
		bounds.width = width;
		bounds.height = height;
	}
	
	public void teleportTo(Entity e) {
		e.setX(x);
		e.setY(y);
	}

	public void tick() {

	}

	public void render(Graphics g) {
		g.drawImage(Assets.portal, (int) (x - handler.getCamera().getxOff()),
				(int) (y - handler.getCamera().getyOff()), 40, 64, null);
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
}