package me.kerdo.shootr.weapons;

import java.awt.Color;
import java.awt.Graphics;

import me.kerdo.shootr.utils.handler.Handler;

public class Bullet {
	private double x, y;
	private double dx, dy;
	private int r;
	private double speed;
	private Handler handler;
	private Color c;
	private float damage;
	private String shooter;
	private int lifeTime;
	private float maxLifeTime;
	
	public Bullet(Handler handler, double angle, double x, double y, double speed, int r, Color c, float damage, String shooter, float maxLifeTime) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.r = r;
		this.damage = damage;
		this.shooter = shooter;
		this.maxLifeTime = maxLifeTime;
		
		dx = Math.cos(angle) * speed;
		dy = Math.sin(angle) * speed;
		this.c = c;
	}
	
	public boolean tick() {
		x += dx;
		y += dy;
		
		if(handler.getWorld().getRangeEnabled()) { //TODO: Make sniper bullets gain more damage after some time.
			lifeTime++;
			if(lifeTime / handler.getGame().getFps() >= maxLifeTime) {
				lifeTime = 0;
				return true;
			}
		}
		
		
		if(x < -r || x > handler.getWidth() - r + handler.getCamera().getxOff() ||
		   y < -r || y > handler.getHeight() - r + handler.getCamera().getyOff()) {
			return true;
		}
		return false;
	}
	
	public void render(Graphics g) {
		g.setColor(c);
		g.fillOval((int) (x - handler.getCamera().getxOff()), (int) (y - handler.getCamera().getyOff()), r * 2, r * 2);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getDx() {
		return dx;
	}

	public void setDx(double dx) {
		this.dx = dx;
	}

	public double getDy() {
		return dy;
	}

	public void setDy(double dy) {
		this.dy = dy;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public boolean collisionWithTile(int x, int y) {
		return handler.getWorld().getTile(x, y).isSolid();
	}
	
	public float getDamage() {
		return damage;
	}

	public String getShooter() {
		return shooter;
	}

	public void setShooter(String shooter) {
		this.shooter = shooter;
	}
}