package me.kerdo.shootr.menu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import me.kerdo.shootr.Game;
import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.gfx.Text;
import me.kerdo.shootr.utils.handler.Handler;

public class PauseMenu extends Menu {
	private String[] options = {"Resume", "Main Menu", "Save", "Load", "Exit"};
	private int currentOption = 0;
	private Color color;
	private String saved;
	private int MAX_LAPSED = 120, lapsed = 0;
	
	public PauseMenu(Handler handler) {
		super(handler);
	}

	@Override
	public void tick() {
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) {
			MenuManager.setMenu(MenuManager.getPreviousMenu());
		} else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN)) {
			currentOption++;
			if(currentOption >= options.length)
				currentOption = 0;
		} else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP)) {
			currentOption--;
			if(currentOption < 0)
				currentOption = options.length - 1;
		} else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)) {
			if(options[currentOption].equalsIgnoreCase("resume")) {
				MenuManager.setMenu(MenuManager.getPreviousMenu());
			} else if(options[currentOption].startsWith("Main")) {
				MenuManager.setMenu(Game.mainmenu);
			}  else if(options[currentOption].equalsIgnoreCase("exit")) {
				System.exit(1);
			} else if(options[currentOption].equalsIgnoreCase("save")) {
				saved = new String(handler.getWorld().saveSave());
				lapsed = MAX_LAPSED;
			} else if(options[currentOption].equalsIgnoreCase("load")) {
				MenuManager.setMenu(Game.loadMenu);
				MenuManager.setPreviousMenu(Game.mainmenu);
			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.mainMenuBG, 0, 0, null);
		for(int i = 0; i < options.length; i++) {
			if(i == currentOption)
				color = DEFAULT_SELECTION_COLOR;
			else
				color = DEFAULT_DESELECTION_COLOR;
			Text.drawString(g, options[i], DEFAULT_MENU_X, DEFAULT_MENU_Y+ (DEFAULT_GAP * i), false, color, Assets.roboto24);
		}
		
		if(lapsed > 0) {
			Text.drawString(g, saved, handler.getWidth() / 2, 400, true, DEFAULT_DESELECTION_COLOR, Assets.roboto24);
			lapsed--;
		}
	}
}