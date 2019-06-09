package me.kerdo.shootr.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import me.kerdo.shootr.entity.creature.Enemy;
import me.kerdo.shootr.entity.creature.Player;
import me.kerdo.shootr.entity.creature.PlayerMP;
import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.gfx.Text;
import me.kerdo.shootr.utils.handler.Handler;

public abstract class Entity {
	public static final int DEFAULT_HEALTH = 100;
	public static final int DEFAULT_XP_ON_DEATH = 5;
	
	protected Handler handler;
	protected float x, y;
	protected int width, height;
	protected int maxHealth, health;
	protected boolean active = true;
	public Rectangle bounds;
	
	public Entity(Handler handler, float x, float y, int width, int height, int maxHealth) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		this.maxHealth = maxHealth;
		if(maxHealth == 0)
			this.maxHealth = DEFAULT_HEALTH;
		health = maxHealth;
		
		bounds = new Rectangle(0, 0, width, height);
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract void die();
	
	public void displayHealth(Graphics g) {
		Text.drawString(g, health + "/" + maxHealth, (int) (x - handler.getCamera().getxOff() + 5), (int) (y + height - handler.getCamera().getyOff() + 15), false, Color.WHITE, Assets.roboto16);
		if(handler.getWorld().getEntityManager().getPlayer().isDev()) {
			g.setColor(Color.WHITE);
			g.drawRect((int) (x + bounds.x - handler.getCamera().getxOff()), (int) (y + bounds.y - handler.getCamera().getyOff()), bounds.width, bounds.height);
		}
	}
	
	public void hurt(int dmg) {
		health -= dmg;
		if(health <= 0) {
			active = false;
			die();
		}
	}
	
	public boolean checkEntityCollisions(float xOff, float yOff) {
		for(Entity e : handler.getWorld().getEntityManager().getEntities()) {
			if(e.equals(this))
				continue;
			if(e instanceof PlayerMP && this instanceof Player)
				continue;
			if(e instanceof Enemy && this instanceof Player)
				continue;
			if(this instanceof Enemy && e instanceof Player)
				continue;
			
			if(e.getCollisionBounds(0, 0).intersects(getCollisionBounds(xOff, yOff))) {
				return true;
			}
		}
		return false;
	}
	
	public Rectangle getCollisionBounds(float xOff, float yOff) {
		return new Rectangle((int) (x + bounds.x + xOff), (int) (y + bounds.y + yOff), bounds.width, bounds.height);
	}

	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
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
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}
	
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}
}