package me.kerdo.shootr.gfx.bar;

import java.awt.Color;
import java.awt.Graphics;

import me.kerdo.shootr.entity.creature.Player;
import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.gfx.Text;

public class Ammobar {
	private Player player;
	private int maxAmmo, currentAmmo;
	
	private double x, y;
	private double width, height;
	private Color color;
	
	public Ammobar(Player target, double x, double y, double width, double height, Color color) {
		this.player = target;
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		this.color = color;
		
		maxAmmo = target.getMaxAmmo();
	}
	
	public void tick() {
		currentAmmo = player.getCurrentAmmo();
		maxAmmo = player.getMaxAmmo();
	}
	
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawRect((int) (x -1), (int) (y - 1), (int) (width + 1), (int) (height + 1));
		g.setColor(color);
		g.fillRect((int) x, (int) y, (int) ((width / maxAmmo) * currentAmmo), (int) height);
		if(player.isReloading())
			Text.drawString(g, "RELOADING", (int) (x + width / 2), (int) (y + height / 2), true, Color.RED, Assets.roboto24);
		else
			Text.drawString(g, currentAmmo + "/" + maxAmmo, (int) (x + width / 2), (int) (y + height / 2), true, Color.WHITE, Assets.roboto24);
	}
}