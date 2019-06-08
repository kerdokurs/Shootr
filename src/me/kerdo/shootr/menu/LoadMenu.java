package me.kerdo.shootr.menu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.gfx.Text;
import me.kerdo.shootr.utils.Utils;
import me.kerdo.shootr.utils.handler.Handler;

public class LoadMenu extends Menu {
	public String[] options;
	private int selectedSave = 0;
	private Color color;
	
	public LoadMenu(Handler handler) {
		super(handler);
		loadAllSaves();
	}

	@Override
	public void tick() {
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN)) {
			if(selectedSave == options.length - 1)
				selectedSave = 0;
			else
				selectedSave++;
		} else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP)) {
			if(selectedSave <= 0)
				selectedSave = options.length - 1;
			else
				selectedSave--;
		} else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)) {
			try {
				MenuManager.setMenu(new GameMenu(handler, options[selectedSave].toLowerCase()));
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) {
			MenuManager.setMenu(MenuManager.getPreviousMenu());
		} else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_SPACE)) {
			loadAllSaves();
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.mainMenuBG, 0, 0, null);
		Text.drawString(g, "Choose a save: ", Menu.DEFAULT_MENU_X, Menu.DEFAULT_MENU_Y, false, Color.WHITE, Assets.roboto24);
		for(int i = 0; i < options.length; i++) {
			if(i == selectedSave)
				color = DEFAULT_SELECTION_COLOR;
			else
				color = DEFAULT_DESELECTION_COLOR;
			Text.drawString(g, options[i], Menu.DEFAULT_MENU_X, Menu.DEFAULT_MENU_Y + 25 + (DEFAULT_GAP * i), false, color, Assets.roboto24);
		}
		
		Text.drawString(g, BACK_TEXT + " SPACE to refresh saves.", 5, handler.getHeight() - 5, false, Color.WHITE, Assets.roboto16);
	}
	
	private void loadAllSaves() {
		String file = Utils.loadFileAsStringFromDisk("/saves/saves.txt");
		file = file.trim();
		options = file.split(",");
		for(int i = 0; i < options.length; i++)
			options[i] = options[i].trim();
	}
}