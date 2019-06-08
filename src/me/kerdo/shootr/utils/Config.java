package me.kerdo.shootr.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import me.kerdo.shootr.Game;

public class Config {
	public Properties prop;
	public String file;
	InputStream input;
	
	public Config(String file) {
		this.file = file;
		prop = new Properties();
		input = Game.class.getResourceAsStream("/" + file);
		try {
			prop.load(input);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public String get(String key) {
		String s = prop.getProperty(key);
		return s;
	}
	
	public void set(String key, String value) {
		prop.setProperty(key, value);
	}
}