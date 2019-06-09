package me.kerdo.shootr.utils.handler;

import me.kerdo.shootr.Game;
import me.kerdo.shootr.gfx.Camera;
import me.kerdo.shootr.input.KeyManager;
import me.kerdo.shootr.input.MouseManager;
import me.kerdo.shootr.worlds.World;

public class Handler {
	private Game game;
	private World world;
	
	public Handler(Game game) {
		this.game = game;
	}
	
	public Camera getCamera() {
		return game.getCamera();
	}
	
	public KeyManager getKeyManager() {
		return game.getKeyManager();
	}
	
	public MouseManager getMouseManager() {
		return game.getMouseManager();
	}
	
	public int getWidth() {
		return game.getWidth();
	}
	
	public int getHeight() {
		return game.getHeight();
	}
	
	public Game getGame() {
		return game;
	}
	
	public void setGame(Game game) {
		this.game = game;
	}
	
	public World getWorld() {
		return world;
	}
	
	public void setWorld(World world) {
		this.world = world;
	}
}