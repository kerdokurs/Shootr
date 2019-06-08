package me.kerdo.shootr.gfx.bar;

import java.awt.Color;
import java.awt.Graphics;

import me.kerdo.shootr.entity.creature.Player;
import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.gfx.Text;

public class Healthbar {
	private Player target;
	private int maxHealth, currentHealth;
	
	private double x, y;
	private double width, height;
	private Color color;
	
	public Healthbar(Player target, double x, double y, double width, double height, Color color) {
		this.target = target;
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		this.color = color;
		
		maxHealth = target.getMaxHealth();
	}
	
	public void tick() {
		currentHealth = target.getHealth();
	}
	
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawRect((int) (x -1), (int) (y - 1), (int) (width + 1), (int) (height + 1));
		g.setColor(color);
		g.fillRect((int) x, (int) y, (int) ((width / maxHealth) * currentHealth), (int) height);
		Text.drawString(g, currentHealth + "/" + maxHealth, (int) (x + width / 2), (int) (y + height / 2), true, Color.WHITE, Assets.roboto24);
	}
}