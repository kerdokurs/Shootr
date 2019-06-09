package me.kerdo.shootr.net.temp;

import com.google.gson.Gson;

import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.weapons.RangedWeapon;

public class Temp {
	public static RangedWeapon pistol = new RangedWeapon(15, RangedWeapon.DEFAULT_SHOOT_TIME, RangedWeapon.DEFAULT_RELOAD_TIME, 12, 7.5, Assets.pistol,
			.035f, RangedWeapon.DEFAULT_MAX_BULLET_TRAVEL_TIME);
	public static RangedWeapon shotgun = new RangedWeapon(150, 1250, RangedWeapon.DEFAULT_RELOAD_TIME * 3, 8, 10, Assets.shotgun, 0f, 1f);
	public static RangedWeapon rifle = new RangedWeapon(20, 105, RangedWeapon.DEFAULT_RELOAD_TIME * 2, 32, 8.75, Assets.rifle, .045f, .75f);
	public static RangedWeapon minigun = new RangedWeapon(5, 25, 2500, 1024, 8.5, Assets.minigun, .075f, .5f);
	
	public Temp() {
		Gson gson = new Gson();
		System.out.println(gson.toJson(new RangedWeapon(15, RangedWeapon.DEFAULT_SHOOT_TIME, RangedWeapon.DEFAULT_RELOAD_TIME, 12, 7.5, Assets.pistol,
				.035f, RangedWeapon.DEFAULT_MAX_BULLET_TRAVEL_TIME)));
	}
}
