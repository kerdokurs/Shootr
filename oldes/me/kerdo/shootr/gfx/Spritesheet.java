package me.kerdo.shootr.gfx;

import java.awt.image.BufferedImage;

public class Spritesheet {
	private BufferedImage sheet;
	
	public Spritesheet(BufferedImage sheet) {
		this.sheet = sheet;
	}
	
	public BufferedImage crop(int x, int y, int width, int height) {
		return sheet.getSubimage(x, y, width, height);
	}
	
	public BufferedImage crop64(int x, int y, int width, int height) {
		return sheet.getSubimage(x * 64, y * 64, width, height);
	}
	
	public BufferedImage crop32(int x, int y, int width, int height) {
		return sheet.getSubimage(x * 32, y * 32, width, height);
	}
	
	public BufferedImage crop16(int x, int y, int width, int height) {
		return sheet.getSubimage(x * 16, y * 16, width, height);
	}
}