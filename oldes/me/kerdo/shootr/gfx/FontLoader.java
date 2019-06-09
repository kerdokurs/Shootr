package me.kerdo.shootr.gfx;

import java.awt.Font;
import java.io.InputStream;

import me.kerdo.shootr.Game;

public class FontLoader {
	public static Font loadFont(String path, float size) {
		try {
			InputStream is = Game.class.getResourceAsStream("/fonts/" + path);
			Font font = Font.createFont(Font.TRUETYPE_FONT, is);
			font = font.deriveFont(Font.PLAIN, size);
			return font;
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
}