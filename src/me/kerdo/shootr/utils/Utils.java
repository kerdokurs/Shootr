package me.kerdo.shootr.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Utils {
  public static String DIR = System.getProperty("user.home") + "/Documents/Shootr/";

  public static String loadFileFromJAR(String path) {
    try {
      final StringBuilder b = new StringBuilder();
      final BufferedReader br = new BufferedReader(new InputStreamReader(Utils.class.getResourceAsStream(path)));

      String line;
      while ((line = br.readLine()) != null)
        b.append(line + "\n");

      br.close();

      return b.toString();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }

  public static String loadFileFromDisk(String path) {
    try {
      //TODO: REMOVE COMMENTS
      final File file = new File(/* DIR + */ path);

      final FileInputStream fis = new FileInputStream(file);
      final StringBuilder sb = new StringBuilder();

      int content;
      while ((content = fis.read()) != -1)
        sb.append((char) content);

      fis.close();

      return sb.toString();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  public static double limitPrecision(final double dbl, final int maxDigitsAfterDecimal) {
    final int multiplier = (int) Math.pow(10, maxDigitsAfterDecimal);
    final double truncated = (double) ((long) (dbl * multiplier)) / multiplier;

    return truncated;
  }
}