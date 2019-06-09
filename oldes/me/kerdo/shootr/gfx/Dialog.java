package me.kerdo.shootr.gfx;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.List;

import me.kerdo.shootr.utils.Utils;

public class Dialog {
	public static String TEXT;
	public static String[] DIALOGS;
	
	private String message;
	private List<String> text;
	private boolean visible = false;
	private Animation anim;
	private int x, y;
	
	public Dialog(String message, int x, int y, float fadeIn, float fadeOut, float time) {
		this.message = message;
		this.x = x;
		this.y = y;
		
		anim = new Animation(150, Assets.dialogArrow);
		parseMessage();
		visible = true;
	}
	
	public static void init() {
		TEXT = Utils.loadFileAsString("/text/dialog.txt").replaceAll("\n", "");
		DIALOGS = TEXT.split("-;");
		System.out.println("xd");
	}
	
	public void render(Graphics g) {
		if(visible) {
			g.drawImage(Assets.dialog, x, y, null);
			for(int i = 0; i < text.size(); i++) {
				Text.drawString(g, text.get(i), x + 8, y + (20 * (i + 1)), false, Color.WHITE, Assets.roboto16);
			}
			
//			Text.drawString(g, message, x + 8, y + 20, false, Color.WHITE, Assets.font16);
			
			Text.drawString(g, "C TO CLOSE", x + 380, y + 110, false, Color.WHITE, Assets.roboto16);
			g.drawImage(anim.getCurrentFrame(), x + 476, y + 96, null);
		}
	}
	
	private void parseMessage() {
		int maxLength = 300;
		if(message.length() > maxLength)
			message = message.substring(0, maxLength);
		
		//TODO: Make word-wrapping work in every situation.
//		StringBuilder sb = new StringBuilder(message);
//
//		int i = 0;
//		while (i + 20 < sb.length() && (i = sb.lastIndexOf(" ", i + 68)) != -1) {
//		    sb.replace(i, i + 1, "\n");
//		}
		
//		message = sb.toString();
		text = Arrays.asList(message.split("\n"));
		
		/*int index = 0;
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < c.length; i++) {
			index++;
			sb.append(c[i]);
			if(index >= 68) {
				lines.add(sb.toString());
				sb = new StringBuilder();
				index = 0;
			}
		}*/
	}
	
	public void tick() {
		anim.tick();
	}
	
	public boolean getVisibility() {
		return visible;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}