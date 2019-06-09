package me.kerdo.shootr.gfx;

import java.awt.Font;
import java.awt.image.BufferedImage;

public class Assets {
	public static final int WIDTH = 32;
	public static final int HEIGHT = 32;
	
	public static Font roboto8, roboto12, roboto16, roboto24, roboto32, roboto64;
	public static Font andy8, andy12, andy16, andy24, andy32, andy64;

	public static BufferedImage air, rock, stone, grass, dirt, water, tree, wood, post;
	public static BufferedImage enemy;
	public static BufferedImage[] player;
	public static BufferedImage terrorist;
	public static BufferedImage mainMenuBG, splashBG;
	public static BufferedImage error, error2;
	public static BufferedImage pistol, rifle, shotgun, minigun, sniper;
	public static BufferedImage inventory, chat, store1;
	public static BufferedImage dialog;
	public static BufferedImage[] dialogArrow;
	public static BufferedImage[] coin;
	public static BufferedImage[] loader;
	
	public static BufferedImage portal;
	public static BufferedImage portalMenu;
	
	public static BufferedImage cursor;
	
	public static BufferedImage[] road;
	
	public static BufferedImage car;

	public static void init() {
		roboto8 = FontLoader.loadFont("Roboto.ttf", 8);
		roboto12 = FontLoader.loadFont("Roboto.ttf", 12);
		roboto16 = FontLoader.loadFont("Roboto.ttf", 16);
		roboto24 = FontLoader.loadFont("Roboto.ttf", 24);
		roboto32 = FontLoader.loadFont("Roboto.ttf", 32);
		roboto64 = FontLoader.loadFont("Roboto.ttf", 64);
		
		andy8 = FontLoader.loadFont("AndyBold.ttf", 8);
		andy12 = FontLoader.loadFont("AndyBold.ttf", 12);
		andy16 = FontLoader.loadFont("AndyBold.ttf", 16);
		andy24 = FontLoader.loadFont("AndyBold.ttf", 24);
		andy32 = FontLoader.loadFont("AndyBold.ttf", 32);
		andy64 = FontLoader.loadFont("AndyBold.ttf", 64);
		
		Spritesheet sheet = new Spritesheet(ImageLoader.loadImage("/textures/spritesheet.png"));
		Spritesheet terrain = new Spritesheet(ImageLoader.loadImage("/textures/terrain.png"));
		Spritesheet vehicles = new Spritesheet(ImageLoader.loadImage("/textures/vehicles.png"));
		
		air = sheet.crop32(0, 0, WIDTH, HEIGHT);
		rock = sheet.crop32(1, 0, WIDTH, HEIGHT);
		stone = sheet.crop32(2, 0, WIDTH, HEIGHT);
		grass = sheet.crop32(3, 0, WIDTH, HEIGHT);
		dirt = sheet.crop32(4, 0, WIDTH, HEIGHT);
		tree = sheet.crop32(0, 1, WIDTH, HEIGHT * 2);
		water = sheet.crop32(4, 1, WIDTH, HEIGHT);
		wood = sheet.crop32(4, 2, WIDTH, HEIGHT);
		post = sheet.crop32(0, 3, WIDTH, HEIGHT * 2);

		player = new BufferedImage[8];
		player[0] = sheet.crop32(5, 0, WIDTH, HEIGHT);
		player[1] = sheet.crop32(6, 0, WIDTH, HEIGHT);
		player[2] = sheet.crop32(7, 0, WIDTH, HEIGHT);
		player[3] = sheet.crop32(8, 0, WIDTH, HEIGHT);
		player[4] = sheet.crop32(9, 0, WIDTH, HEIGHT);
		player[5] = sheet.crop32(10, 0, WIDTH, HEIGHT);
		player[6] = sheet.crop32(11, 0, WIDTH, HEIGHT);
		player[7] = sheet.crop32(12, 0, WIDTH, HEIGHT);
		
		enemy = sheet.crop32(13, 0, WIDTH, HEIGHT);
		
		terrorist = sheet.crop32(12, 0, WIDTH, HEIGHT);
		
		error = sheet.crop32(3, 1, WIDTH, HEIGHT);
		error2 = sheet.crop32(3, 2, WIDTH, HEIGHT);
		
		rifle = sheet.crop32(6, 1, WIDTH * 2, HEIGHT);
		shotgun = sheet.crop32(8, 1, WIDTH * 2, HEIGHT);
		pistol = sheet.crop32(10, 1, WIDTH * 2, HEIGHT);
		minigun = sheet.crop32(12, 1, WIDTH * 2, HEIGHT);
		sniper = sheet.crop32(14, 1, WIDTH * 2, HEIGHT);
		
		inventory = ImageLoader.loadImage("/textures/inventory.png");
		chat = ImageLoader.loadImage("/textures/chat.png");
		
		portalMenu = ImageLoader.loadImage("/textures/portalMenu.png");
		Spritesheet portals = new Spritesheet(ImageLoader.loadImage("/textures/portals.png"));
		portal = portals.crop(0, 0, 10, 16);
		
		road = new BufferedImage[12];
		road[0] = terrain.crop32(0, 0, WIDTH, HEIGHT);
		road[1] = terrain.crop32(1, 0, WIDTH, HEIGHT);
		road[2] = terrain.crop32(2, 0, WIDTH, HEIGHT);
		road[3] = terrain.crop32(0, 1, WIDTH, HEIGHT);
		road[4] = terrain.crop32(1, 1, WIDTH, HEIGHT);
		road[5] = terrain.crop32(2, 1, WIDTH, HEIGHT);
		road[6] = terrain.crop32(0, 2, WIDTH, HEIGHT);
		road[7] = terrain.crop32(1, 2, WIDTH, HEIGHT);
		road[8] = terrain.crop32(2, 2, WIDTH, HEIGHT);
		road[9] = terrain.crop32(3, 0, WIDTH, HEIGHT);
		road[10] = terrain.crop32(3, 1, WIDTH, HEIGHT);
		road[11] = terrain.crop32(3, 2, WIDTH, HEIGHT);
		
		car = vehicles.crop32(0, 0, WIDTH * 2, HEIGHT);
		
		store1 = ImageLoader.loadImage("/textures/mainmenuBG.png");
		
		dialog = ImageLoader.loadImage("/textures/dialog.png");
		Spritesheet dialogArrows = new Spritesheet(ImageLoader.loadImage("/textures/dialog-arrow.png"));
		dialogArrow = new BufferedImage[3];
		dialogArrow[0] = dialogArrows.crop16(0, 0, 16, 16);
		dialogArrow[1] = dialogArrows.crop16(1, 0, 16, 16);
		dialogArrow[2] = dialogArrows.crop16(2, 0, 16, 16);
		
		coin = new BufferedImage[6];
		Spritesheet coins = new Spritesheet(ImageLoader.loadImage("/textures/coin.png"));
		for(int i = 0; i < 6; i++)
			coin[i] = coins.crop16(i, 0, 16, 16);
		
		loader = new BufferedImage[8];
		Spritesheet loading = new Spritesheet(ImageLoader.loadImage("/textures/loading.png"));
		for(int i = 0; i < 8; i++)
			loader[i] = loading.crop16(i, 0, 16, 16);
		
		cursor = ImageLoader.loadImage("/textures/cursor.png");
		
		mainMenuBG = ImageLoader.loadImage("/textures/mainmenuBG.png");
		splashBG = ImageLoader.loadImage("/textures/splash.png");
	}
}