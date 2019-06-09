package me.kerdo.shootr.weapons;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.kerdo.shootr.utils.handler.Handler;

public class RangedWeapon {
	public static final float DEFAULT_DAMAGE = 10f;
	public static final int DEFAULT_SHOOT_TIME = 350;
	public static final int DEFAULT_RELOAD_TIME = 1000;
	public static final int DEFAULT_CLIP_SIZE = 20;
	public static final double DEFAULT_BULLET_SPEED = 3;
	public static final float DEFAULT_MAX_BULLET_TRAVEL_TIME = 1.5f;

	private float damage;
	private int shootTime;
	private int reloadTime;
	private int clipSize;
	private int bullets;
	private double bulletSpeed;
	private BufferedImage texture;
	private float inaccuracy;
	private float maxBulletTravelTime;
	
	public static RangedWeapon pistol, shotgun, rifle, minigun, sniper;

//	public static Weapon pistol = new Weapon(15, DEFAULT_SHOOT_TIME, DEFAULT_RELOAD_TIME, 12, 7.5, Assets.pistol,
//			.035f, DEFAULT_MAX_BULLET_TRAVEL_TIME);
//	public static Weapon shotgun = new Weapon(150, 1250, DEFAULT_RELOAD_TIME * 3, 8, 10, Assets.shotgun, 0f, 1f);
//	public static Weapon rifle = new Weapon(20, 105, DEFAULT_RELOAD_TIME * 2, 32, 8.75, Assets.rifle, .045f, .75f);
//	public static Weapon minigun = new Weapon(5, 25, 2500, 1024, 8.5, Assets.minigun, .075f, .5f);

	private Handler handler;

	public RangedWeapon(float damage, int shootTime, int reloadTime, int clipSize, double bulletSpeed,
			BufferedImage texture, float inaccuracy, float maxBulletTravelTime) {
		this.damage = damage;
		this.shootTime = shootTime;
		this.reloadTime = reloadTime;
		this.clipSize = clipSize;
		bullets = clipSize;
		this.bulletSpeed = bulletSpeed;
		this.texture = texture;
		this.inaccuracy = inaccuracy;
		this.maxBulletTravelTime = maxBulletTravelTime;
	}

	public void tick() {

	}

	public void render(Graphics g) {
		g.drawImage(texture, 40, 50, null);
	}

	public double getBulletspeed() {
		return bulletSpeed;
	}

	public void setBulletspeed(double speed) {
		bulletSpeed = speed;
	}

	public float getDamage() {
		return damage;
	}

	public void setDamage(float damage) {
		this.damage = damage;
	}

	public int getShootTime() {
		return shootTime;
	}

	public void setShootTime(int shootTime) {
		this.shootTime = shootTime;
	}

	public int getReloadTime() {
		return reloadTime;
	}

	public void setReloadTime(int reloadTime) {
		this.reloadTime = reloadTime;
	}

	public int getClipSize() {
		return clipSize;
	}

	public void setClipSize(int clipSize) {
		this.clipSize = clipSize;
	}

	public int getBullets() {
		return bullets;
	}

	public void setBullets(int bullets) {
		this.bullets = bullets;
	}

	public BufferedImage getTexture() {
		return texture;
	}

	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}

	public float getInaccuracy() {
		return inaccuracy;
	}

	public float getMaxBulletTravelTime() {
		return maxBulletTravelTime;
	}

	public void setMaxBulletTravelTime(float maxBulletTravelTime) {
		this.maxBulletTravelTime = maxBulletTravelTime;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}
}