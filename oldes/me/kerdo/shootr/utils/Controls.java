package me.kerdo.shootr.utils;

public class Controls {
	public static int UP, DOWN, LEFT, RIGHT;
	public static int SLOT1, SLOT2, SLOT3, SLOT4, SLOT5, RELOAD;
	public static int SHOOT, WALK, CROUCH;
	public static int CHAT, INVENTORY, DIALOG;
	public static int VOLUME; //SLIDERS
	
	private static Config2 keys;
	
	public static void init() {
		keys = new Config2("keys.ini");
		
		UP = Integer.parseInt(keys.get("UP"));
		DOWN = Integer.parseInt(keys.get("DOWN"));
		LEFT = Integer.parseInt(keys.get("LEFT"));
		RIGHT = Integer.parseInt(keys.get("RIGHT"));
		SLOT1 = Integer.parseInt(keys.get("SLOT1"));
		SLOT2 = Integer.parseInt(keys.get("SLOT2"));
		SLOT3 = Integer.parseInt(keys.get("SLOT3"));
		SLOT4 = Integer.parseInt(keys.get("SLOT4"));
		SLOT5 = Integer.parseInt(keys.get("SLOT5"));
		SHOOT = Integer.parseInt(keys.get("SHOOT"));
		WALK = Integer.parseInt(keys.get("WALK"));
		CROUCH = Integer.parseInt(keys.get("CROUCH"));
		CHAT = Integer.parseInt(keys.get("CHAT"));
		INVENTORY = Integer.parseInt(keys.get("INVENTORY"));
		DIALOG = Integer.parseInt(keys.get("DIALOG"));
		RELOAD = Integer.parseInt(keys.get("RELOAD"));
		
		VOLUME = Integer.parseInt(keys.get("VOLUME"));
	}
	
	public static void set(String key, String value) {
		System.out.println("Setting " + key + " to " + value);
		keys.set(key, value);
	}
}