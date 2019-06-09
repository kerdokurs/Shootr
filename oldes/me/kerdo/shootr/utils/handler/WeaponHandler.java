package me.kerdo.shootr.utils.handler;

import java.util.ArrayList;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.utils.Utils;
import me.kerdo.shootr.weapons.RangedWeapon;

public class WeaponHandler {
	ArrayList<RangedWeapon> allWeapons;

	public WeaponHandler(Handler handler, String file) {
		allWeapons = new ArrayList<RangedWeapon>();
		parseFile(file);
	}

	public void parseFile(String file) {
		String data = Utils.loadFileAsStringFromDisk(file + ".json");
		JsonParser jp = new JsonParser();
		JsonElement base = jp.parse(data);
		JsonObject main = base.getAsJsonObject();

		JsonObject weapon = main.get("pistol").getAsJsonObject();
		allWeapons.add(new RangedWeapon(weapon.get("damage").getAsFloat(), weapon.get("shoot_time").getAsInt(),
				weapon.get("reload_time").getAsInt(), weapon.get("clip_size").getAsInt(),
				weapon.get("bullet_speed").getAsDouble(), Assets.pistol, weapon.get("inaccuracy").getAsFloat(),
				weapon.get("max_bullet_life_time").getAsFloat()));
		weapon = main.get("shotgun").getAsJsonObject();
		allWeapons.add(new RangedWeapon(weapon.get("damage").getAsFloat(), weapon.get("shoot_time").getAsInt(),
				weapon.get("reload_time").getAsInt(), weapon.get("clip_size").getAsInt(),
				weapon.get("bullet_speed").getAsDouble(), Assets.shotgun, weapon.get("inaccuracy").getAsFloat(),
				weapon.get("max_bullet_life_time").getAsFloat()));
		weapon = main.get("rifle").getAsJsonObject();
		allWeapons.add(new RangedWeapon(weapon.get("damage").getAsFloat(), weapon.get("shoot_time").getAsInt(),
				weapon.get("reload_time").getAsInt(), weapon.get("clip_size").getAsInt(),
				weapon.get("bullet_speed").getAsDouble(), Assets.rifle, weapon.get("inaccuracy").getAsFloat(),
				weapon.get("max_bullet_life_time").getAsFloat()));
		weapon = main.get("minigun").getAsJsonObject();
		allWeapons.add(new RangedWeapon(weapon.get("damage").getAsFloat(), weapon.get("shoot_time").getAsInt(),
				weapon.get("reload_time").getAsInt(), weapon.get("clip_size").getAsInt(),
				weapon.get("bullet_speed").getAsDouble(), Assets.minigun, weapon.get("inaccuracy").getAsFloat(),
				weapon.get("max_bullet_life_time").getAsFloat()));
		weapon = main.get("sniper").getAsJsonObject();
		allWeapons.add(new RangedWeapon(weapon.get("damage").getAsFloat(), weapon.get("shoot_time").getAsInt(),
				weapon.get("reload_time").getAsInt(), weapon.get("clip_size").getAsInt(),
				weapon.get("bullet_speed").getAsDouble(), Assets.sniper, weapon.get("inaccuracy").getAsFloat(),
				weapon.get("max_bullet_life_time").getAsFloat()));
	}
	
	public ArrayList<RangedWeapon> getWeapons() {
		return allWeapons;
	}
}