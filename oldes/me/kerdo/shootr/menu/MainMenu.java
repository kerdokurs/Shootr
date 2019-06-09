package me.kerdo.shootr.menu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import com.google.gson.Gson;

import me.kerdo.shootr.net.kPacket;
import me.kerdo.shootr.Game;
import me.kerdo.shootr.gfx.Animation;
import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.gfx.Text;
import me.kerdo.shootr.net.Client;
import me.kerdo.shootr.utils.Controls;
import me.kerdo.shootr.utils.handler.Handler;

public class MainMenu extends Menu {
	private Gson gson;
	private String[] choice = {"New", "Load", "Settings", "Credits", "Exit"};
	private int currentChoice = 0;
	private Color color;
	private boolean loaded = false;
	
	private Animation loader;

	public MainMenu(Handler handler) {
		super(handler);
		gson = new Gson();
		Controls.init();
		
		loader = new Animation(225, Assets.loader);
	}

	@Override
	public void tick() {	
		if(loaded) {
			if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN)) {
				if(currentChoice == choice.length - 1)
					currentChoice = 0;
				else
					currentChoice++;
			} else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP)) {
				if(currentChoice <= 0)
					currentChoice = choice.length - 1;
				else
					currentChoice--;
			} else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)) {
				if(choice[currentChoice].equalsIgnoreCase("new")) {
					MenuManager.setMenu(Game.newMenu);
				} else if(choice[currentChoice].equalsIgnoreCase("load")) {
					MenuManager.setMenu(Game.loadMenu);
				} else if(choice[currentChoice].equalsIgnoreCase("multiplayer")) {
					handler.getGame().setClient(new Client(handler, "localhost"));
					handler.getGame().getClient().start();
					kPacket up = new kPacket("UP", 0);
					handler.getGame().getClient().sendData(gson.toJson(up).getBytes());
				} else if(choice[currentChoice].equalsIgnoreCase("credits")) {
					Game.creditsMenu = new CreditsMenu(handler);
					MenuManager.setMenu(Game.creditsMenu);
				} else if(choice[currentChoice].equalsIgnoreCase("settings")) {
					MenuManager.setMenu(Game.settingsMenu);
				} else if(choice[currentChoice].equalsIgnoreCase("exit")) {
					System.exit(1);
				}
			}
		} else {
			if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)) {
				loaded = true;
			}
			loader.tick();
		}
	}

	@Override
	public void render(Graphics g) {
		if(loaded) {
			g.drawImage(Assets.mainMenuBG, 0, 0, null);
			
			for(int i = 0; i < choice.length; i++) {
				if(i == currentChoice)
					color = DEFAULT_SELECTION_COLOR;
				else
					color = DEFAULT_DESELECTION_COLOR;
				Text.drawString(g, choice[i], Menu.DEFAULT_MENU_X, Menu.DEFAULT_MENU_Y + (DEFAULT_GAP * i), false, color,
						Assets.roboto24);
			}
		} else {
			g.drawImage(Assets.splashBG, 0, 0, null);
			Text.drawString(g, "Press ENTER to continue", handler.getWidth() / 2, handler.getHeight() - 80, true, Color.WHITE, Assets.andy32);
			g.drawImage(loader.getCurrentFrame(), handler.getWidth() - 48, handler.getHeight() - 48, 32, 32, null);
		}
	}
}