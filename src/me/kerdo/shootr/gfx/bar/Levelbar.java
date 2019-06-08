package me.kerdo.shootr.gfx.bar;

import java.awt.Color;
import java.awt.Graphics;

import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.gfx.Text;
import me.kerdo.shootr.utils.Utils;
import me.kerdo.shootr.utils.handler.Handler;

public class Levelbar {
	private Handler handler;
	private int xpNeededToNextLvl = 100;
	
	private float lvlMultiplier = 1f;
	private float deathMultiplier = 1f;
	
	private int currentXp = 0;
	private int currentLvl = 1;
	
	private int x, y;
	private double width, height;
	
	private Color color;
	
	private int lvlUpTextTime = 90, lvlUpTextTimeNow = lvlUpTextTime;
	private boolean lvldUp = false;
	
	public Levelbar(Handler handler, int x, int y, int width, int height, Color color) {
		this.handler = handler;
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		this.color = color;
	}
	
	public void tick() {
		if(lvldUp) {
			lvlUpTextTimeNow--;
			if(lvlUpTextTimeNow <= 0) {
				lvldUp = false;
				lvlUpTextTimeNow = lvlUpTextTime;
			}
		}
		
		if(currentXp >= xpNeededToNextLvl) {
			currentLvl++;
			xpNeededToNextLvl = (int) Utils.limitPrecision(Float.toString(xpNeededToNextLvl * 2f), 0, false);
			lvldUp = true;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawRect((int) (x -1), (int) (y - 1), (int) (width + 1), (int) (height + 1));
		g.setColor(color);
		g.fillRect((int) x, (int) y, (int) ((width / xpNeededToNextLvl) * currentXp), (int) height);
		Text.drawString(g, "(" + currentLvl + ")", (int) (x + 15), (int) (y + height / 2), true, Color.BLACK, Assets.roboto24);
		Text.drawString(g, currentXp + "/" + xpNeededToNextLvl, (int) (x + width / 2), (int) (y + height / 2), true, Color.BLACK, Assets.roboto24);
		
		if(lvldUp)
			Text.drawString(g, "Congratulations! You leveled up!", handler.getWidth() / 2, handler.getHeight() / 2, true, Color.WHITE, Assets.roboto32);
	}
	
	public void addXP(int xp) {
		currentXp += xp;
	}
	
	public void removeXP(int xp) {
		currentXp -= xp;
	}
	
	public int getCurrentLevel() {
		return currentLvl;
	}
	
	public void setCurrentLevel(int level) {
		currentLvl = level;
	}
	
	public int getCurrentXP() {
		return currentXp;
	}
	
	public void setCurrentXp(int xp) {
		currentXp = xp;
	}

	public float getLvlMultiplier() {
		return lvlMultiplier;
	}

	public void setLvlMultiplier(float lvlMultiplier) {
		this.lvlMultiplier = lvlMultiplier;
	}

	public float getDeathMultiplier() {
		return deathMultiplier;
	}

	public void setDeathMultiplier(float deathMultiplier) {
		this.deathMultiplier = deathMultiplier;
	}
}