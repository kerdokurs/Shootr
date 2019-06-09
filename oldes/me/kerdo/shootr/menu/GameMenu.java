package me.kerdo.shootr.menu;

import java.awt.Graphics;

import me.kerdo.shootr.utils.handler.Handler;
import me.kerdo.shootr.worlds.World;

public class GameMenu extends Menu {
	
	private World world;
	
	public GameMenu(Handler handler, String save) {
		super(handler);
		world = new World(handler, save);
		handler.setWorld(world);
	}

	@Override
	public void tick() {
		world.tick();
	}

	@Override
	public void render(Graphics g) {
		world.render(g);
	}
}