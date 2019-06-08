package me.kerdo.shootr.entity.creature;

import me.kerdo.shootr.entity.Entity;
import me.kerdo.shootr.tile.Tile;
import me.kerdo.shootr.utils.handler.Handler;

public abstract class Creature extends Entity {
	public static final float DEFAULT_SPEED = 3f;
	public static final int DEFAULT_WIDTH = 64,
							DEFAULT_HEIGHT = 64;
	
	protected float speed;
	protected float xMove, yMove;

	public Creature(Handler handler, float x, float y, int width, int height, int maxHealth) {
		super(handler, x, y, width, height, maxHealth);
		speed = DEFAULT_SPEED;
		xMove = 0;
		yMove = 0;
	}
	
	public void move() {
		if(!checkEntityCollisions(xMove, 0))
			moveX();
		if(!checkEntityCollisions(0, yMove))
			moveY();
	}
	
	public void moveX() {
		if(xMove > 0) {
			int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILE_WIDTH;
			
			if(!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILE_HEIGHT) &&
			   !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILE_HEIGHT)) {
				x += xMove;
			} else {
				x = tx * Tile.TILE_WIDTH - bounds.x - bounds.width - 1;
			}
		} else if(xMove < 0) {
			int tx = (int) (x + xMove + bounds.x) / Tile.TILE_WIDTH;
			
			if(!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILE_HEIGHT) &&
			   !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILE_HEIGHT)) {
				x += xMove;
			} else {
				x = tx * Tile.TILE_WIDTH + Tile.TILE_WIDTH - bounds.x;
			}
		}
	}
	
	public void moveY() {
		if(yMove < 0) {
			int ty = (int) (y + yMove + bounds.y) / Tile.TILE_HEIGHT;
			
			if(!collisionWithTile((int) (x + bounds.x) / Tile.TILE_WIDTH, ty) &&
			   !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILE_WIDTH, ty)) {
				y += yMove;
			} else {
				y = ty * Tile.TILE_HEIGHT + Tile.TILE_HEIGHT - bounds.y;
			}
		} else if(yMove > 0) {
			int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILE_HEIGHT;
			
			if(!collisionWithTile((int) (x + bounds.x) / Tile.TILE_WIDTH, ty) &&
			   !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILE_WIDTH, ty)) {
				y += yMove;
			} else {
				y = ty * Tile.TILE_HEIGHT - bounds.y - bounds.height - 1;
			}
		}
	}
	
	protected boolean collisionWithTile(int x, int y) {
		return handler.getWorld().getTile(x, y).isSolid();
	}
	
	public static int getDefaultHealth() {
		return DEFAULT_HEALTH;
	}
	public static float getDefaultSpeed() {
		return DEFAULT_SPEED;
	}

	public float getxMov() {
		return xMove;
	}

	public void setxMov(float xMov) {
		this.xMove = xMov;
	}

	public float getyMov() {
		return yMove;
	}

	public void setyMov(float yMov) {
		this.yMove = yMov;
	}
	
}