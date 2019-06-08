package me.kerdo.shootr.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Config2 {
	public Properties prop;
	public String file;
	private InputStream input;
	private OutputStream output;
	
	public Config2(String file) {
		this.file = file;
		prop = new Properties();
		try {
			input = new FileInputStream(Utils.DIR + file);
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
		try {
			output = new FileOutputStream(Utils.DIR + file);
			prop.setProperty(key, value);
			prop.store(output, null);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
