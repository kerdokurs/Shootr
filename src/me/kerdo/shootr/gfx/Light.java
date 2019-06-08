package me.kerdo.shootr.gfx;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;

import me.kerdo.shootr.utils.handler.Handler;

public class Light {
	private float x, y;
	private float radius;
	private Handler handler;
	
	public Light(Handler handler, float x, float y, float radius) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.radius = radius;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		Point2D center = new Point2D.Float(x, y);
		float[] distance = {0.0f, 1.0f};
		Color[] colors = {new Color(0.0f, 0.0f, 0.0f, 0.0f), Color.BLACK};
		RadialGradientPaint p = new RadialGradientPaint(center, radius, distance, colors);
		g2d.setPaint(p);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .95f));
		g2d.fillRect(0, 0, handler.getWidth(), handler.getHeight());
		g2d.dispose();
	}
}