package me.kerdo.shootr.tile;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.kerdo.shootr.gfx.Assets;

public class Tile {
	public static Tile[] tiles = new Tile[256];
	public static Tile airTile = new Tile("Air", 0, Assets.air, true);
	public static Tile rockTile = new Tile("Rock", 1, Assets.rock, true);
	public static Tile stoneTile = new Tile("Stone", 2, Assets.stone, true);
	public static Tile grassTile = new Tile("Grass", 3, Assets.grass, false);
	public static Tile dirtTile = new Tile("Dirt", 4, Assets.dirt, false);
	public static Tile waterTile = new Tile("Water", 5, Assets.water, false);
	public static Tile errorTile = new Tile("Error", 6, Assets.error, false);
	
	public static Tile roadTile1 = new Tile("Road1", 7, Assets.road[0], false);
	public static Tile roadTile2 = new Tile("Road2", 8, Assets.road[1], false);
	public static Tile roadTile3 = new Tile("Road3", 9, Assets.road[2], false);
	public static Tile roadTile4 = new Tile("Road4", 10, Assets.road[3], false);
	public static Tile roadTile5 = new Tile("Road5", 11, Assets.road[4], false);
	public static Tile roadTile6 = new Tile("Road6", 12, Assets.road[5], false);
	public static Tile roadTile7 = new Tile("Road7", 13, Assets.road[6], false);
	public static Tile roadTile8 = new Tile("Road8", 14, Assets.road[7], false);
	public static Tile roadTile9 = new Tile("Road9", 15, Assets.road[8], false);
	public static Tile roadTile10 = new Tile("Road10", 16, Assets.road[9], false);
	public static Tile roadTile11 = new Tile("Road11", 17, Assets.road[10], false);
	public static Tile roadTile12 = new Tile("Road12", 18, Assets.road[11], false);
	
	private String name;
	private BufferedImage texture;
	private final int id;
	private final boolean solid;
	
	
	public static final int TILE_WIDTH = 64,
							TILE_HEIGHT = 64;
	
	public Tile(String name, int id, BufferedImage texture, boolean solid) {
		this.name = name;
		this.texture = texture;
		this.id = id;
		this.solid = solid;
		
		tiles[id] = this;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g, int x, int y) {
		g.drawImage(texture, x, y, TILE_WIDTH, TILE_HEIGHT, null);
	}
	
	public boolean isSolid() {
		return solid;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
}