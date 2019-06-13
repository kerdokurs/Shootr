package me.kerdo.shootr;

public class Launcher {
  public static void main(final String[] args) {
    final Game game = new Game("Shootr", 1080, 660);
    game.start();
  }
}
