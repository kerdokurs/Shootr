package me.kerdo.shootr.menu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.gfx.Text;
import me.kerdo.shootr.utils.Utils;
import me.kerdo.shootr.utils.handler.Handler;

public class CreditsMenu extends Menu {
	private String[] creds;
	private int default_o = 660, o = default_o;
	private boolean stopped = false;
	
	public CreditsMenu(Handler handler) {
		super(handler);
		creds = Utils.loadFileAsString("/text/credits.txt").split("\n");
	}

	@Override
	public void tick() {
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) {
			MenuManager.setMenu(MenuManager.getPreviousMenu());
		} else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)) {
			stopped = !stopped;
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.splashBG, 0, 0, null);
		
		for(int i = 0; i < creds.length; i++) {
			Text.drawString(g, creds[i], handler.getWidth() / 2, o + (25 * i), true, new Color(211, 211, 211, 255), Assets.andy24);
		}
		
		Text.drawString(g, BACK_TEXT + ", ENTER to pause", 5, handler.getHeight() - 5, false, Color.WHITE, Assets.roboto16);
		
		if(!stopped)
			o--;
		if(o + (25 * creds.length) <= 0)
			o = default_o + 10;
	}
}