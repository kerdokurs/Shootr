package me.kerdo.shootr.menu;

import java.awt.Color;
import java.awt.Graphics;

import me.kerdo.shootr.utils.handler.Handler;

public abstract class Menu {
	public static int DEFAULT_MENU_X = 25, DEFAULT_MENU_Y = 400, DEFAULT_GAP = 30;
	public static String BACK_TEXT = "Press ESCAPE to go back";
	public static Color DEFAULT_SELECTION_COLOR = new Color(0, 127, 14, 255), DEFAULT_DESELECTION_COLOR = Color.WHITE;
	
	protected Handler handler;
	
	public Menu(Handler handler) {
		this.handler = handler;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
}