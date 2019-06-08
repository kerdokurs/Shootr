package me.kerdo.shootr.weapons;

import java.awt.Graphics;
import java.util.ArrayList;

import me.kerdo.shootr.utils.handler.Handler;
import me.kerdo.shootr.utils.handler.WeaponHandler;

public class RangedWeaponManager {
	private ArrayList<RangedWeapon> weapons;
	private Handler handler;

	private WeaponHandler weaponHandler;

	public RangedWeaponManager(Handler handler) {
		weapons = new ArrayList<RangedWeapon>();
		this.handler = handler;

		weaponHandler = new WeaponHandler(handler, "weapons");
		ArrayList<RangedWeapon> weapons = weaponHandler.getWeapons();
		RangedWeapon w = weapons.get(0);
		RangedWeapon.pistol = new RangedWeapon(w.getDamage(), w.getShootTime(), w.getReloadTime(), w.getClipSize(),
				w.getBulletspeed(), w.getTexture(), w.getInaccuracy(), w.getMaxBulletTravelTime());
		w = weapons.get(1);
		RangedWeapon.shotgun = new RangedWeapon(w.getDamage(), w.getShootTime(), w.getReloadTime(), w.getClipSize(),
				w.getBulletspeed(), w.getTexture(), w.getInaccuracy(), w.getMaxBulletTravelTime());
		w = weapons.get(2);
		RangedWeapon.rifle = new RangedWeapon(w.getDamage(), w.getShootTime(), w.getReloadTime(), w.getClipSize(),
				w.getBulletspeed(), w.getTexture(), w.getInaccuracy(), w.getMaxBulletTravelTime());
		w = weapons.get(3);
		RangedWeapon.minigun = new RangedWeapon(w.getDamage(), w.getShootTime(), w.getReloadTime(), w.getClipSize(),
				w.getBulletspeed(), w.getTexture(), w.getInaccuracy(), w.getMaxBulletTravelTime());
		w = weapons.get(4);
		RangedWeapon.sniper = new RangedWeapon(w.getDamage(), w.getShootTime(), w.getReloadTime(), w.getClipSize(),
				w.getBulletspeed(), w.getTexture(), w.getInaccuracy(), w.getMaxBulletTravelTime());
	}

	public void shoot(Handler handler, RangedWeapon w) {

	}

	public void tick() {
		for(RangedWeapon w : weapons) {
			w.tick();
		}
	}

	public void render(Graphics g) {
		for(RangedWeapon w : weapons) {
			w.render(g);
		}

		if(!handler.getWorld().getEntityManager().getPlayer().getInventory().isActive())
			g.drawImage(handler.getWorld().getEntityManager().getPlayer().getCurrentWeapon().getTexture(),
					handler.getWidth() - 202, handler.getHeight() - 244, 128, 64, null);
	}

	public void addWeapon(RangedWeapon w) {
		weapons.add(w);
	}

	public ArrayList<RangedWeapon> getWeapons() {
		return weapons;
	}
}