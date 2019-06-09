package me.kerdo.shootr.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import me.kerdo.shootr.worlds.World;

public class Utils {
	public static String DIR = System.getProperty("user.home") + "/Documents/Shootr/";
	public static String loadFileAsString(String path) {
		StringBuilder b = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(World.class.getResourceAsStream(path)));
			String line;
			while((line = br.readLine()) != null)
				b.append(line + "\n");
			br.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return b.toString();
	}
	
	public static String loadFileAsStringFromDisk(String path) {
		File file = new File(DIR + path);
		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream(file);
			StringBuilder sb = new StringBuilder();
			
			int content;
			while((content = fis.read()) != -1) {
				sb.append((char) content);
			}
			fis.close();
			return sb.toString();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static int parseInt(String num) {
		try {
			return Integer.parseInt(num);
		} catch(NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static double limitPrecision(String dblAsString, int maxDigitsAfterDecimal, boolean announce) {
	    int multiplier = (int) Math.pow(10, maxDigitsAfterDecimal);
	    double truncated = (double) ((long) ((Double.parseDouble(dblAsString)) * multiplier)) / multiplier;
	    if(announce)
	    	System.out.println(dblAsString + " ==> " + truncated);
	    return truncated;
	}
}