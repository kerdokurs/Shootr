package me.kerdo.shootr.menu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.gfx.Text;
import me.kerdo.shootr.utils.Controls;
import me.kerdo.shootr.utils.handler.Handler;

public class SettingsMenu extends Menu {
	private String[] keys = {"UP", "DOWN", "LEFT", "RIGHT", "SLOT1", "SLOT2", "SLOT3", "SLOT4", "SLOT5", "SHOOT",
			"INVENTORY", "WALK", "CROUCH", "CHAT", "DIALOG", "RELOAD", "VOLUME"};
	private int[] keyNum = {Controls.UP, Controls.DOWN, Controls.LEFT, Controls.RIGHT, Controls.SLOT1, Controls.SLOT2,
			Controls.SLOT3, Controls.SLOT4, Controls.SLOT5, Controls.SHOOT, Controls.INVENTORY, Controls.WALK,
			Controls.CROUCH, Controls.CHAT, Controls.DIALOG, Controls.RELOAD, Controls.VOLUME};
	
	private int currentSettingIndex;
	private Handler handler;
	private int editingIndex;
	public boolean editing;

	public SettingsMenu(Handler handler) {
		super(handler);
		this.handler = handler;
	}

	@Override
	public void tick() {
		getInput();
	}

	public void getInput() {
		if(!editing) {
			if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN)) {
				if(currentSettingIndex == keys.length - 1)
					currentSettingIndex = 0;
				else
					currentSettingIndex++;
			} else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP)) {
				if(currentSettingIndex <= 0)
					currentSettingIndex = keys.length - 1;
				else
					currentSettingIndex--;
			} else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)) {
				if(!keys[currentSettingIndex].equalsIgnoreCase("volume")) {
					edit();
				}
			} else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) {
				int vol = 100;
				for(int i = 0; i < keys.length; i++) {
					if(keys[i].equalsIgnoreCase("volume")) {
						vol = keyNum[i];
					}
				}
				Controls.set("VOLUME", Integer.toString(vol));
				Controls.init();
				MenuManager.setMenu(MenuManager.getPreviousMenu());
			} else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_LEFT)) {
				if(keyNum[currentSettingIndex] != 0)
					keyNum[currentSettingIndex] -= 5;
			}else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_RIGHT)) {
				if(keyNum[currentSettingIndex] != 100)
					keyNum[currentSettingIndex] += 5;
			}
		} else {
			if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) {
				editing = false;
			}
			for(int i = 0; i < handler.getKeyManager().keys.length; i++) {
				if(handler.getKeyManager().keyJustPressed(i)) {
					save(i);
				}
			}
		}
	}

	public void edit() {
		if(!editing) {
			editingIndex = currentSettingIndex;
			editing = true;
		}
	}

	public void save(int keyCode) {
		if(editing) {
			keyNum[editingIndex] = keyCode;
			editing = false;
			Controls.set(keys[editingIndex], Integer.toString(keyNum[editingIndex]));
			Controls.init();
		}
	}

	@Override
	public void render(Graphics g) {
		Color color = Color.WHITE;
		g.drawImage(Assets.mainMenuBG, 0, 0, null);
		for(int i = 0; i < keys.length; i++) {
			if(i == currentSettingIndex)
				color = DEFAULT_SELECTION_COLOR;
			else
				color = DEFAULT_DESELECTION_COLOR;
			if(!keys[i].equalsIgnoreCase("volume")) {
				Text.drawString(g, keys[i] + "   -   " + KeyEvent.getKeyText(keyNum[i]), 80, 60 + (DEFAULT_GAP * i), false, color,
						Assets.roboto24);
			} else {
				Text.drawString(g, keys[i] + "   -   " + keyNum[i], 80, 60 + (DEFAULT_GAP * i), false, color,
						Assets.roboto24);
			}
		}
		
		Text.drawString(g, BACK_TEXT, 5, handler.getHeight() - 5, false, Color.WHITE, Assets.roboto16);
	}
}