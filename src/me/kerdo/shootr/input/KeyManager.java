package me.kerdo.shootr.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// TODO Rework
public class KeyManager implements KeyListener {
  public final boolean[] keys, justPressed, cantPress;

  public KeyManager() {
    keys = new boolean[256];
    justPressed = new boolean[keys.length];
    cantPress = new boolean[keys.length];
  }

  public void tick() {
    for (int i = 0; i < keys.length; i++) {
      if (cantPress[i] && !keys[i]) {
        cantPress[i] = false;
      } else if (justPressed[i]) {
        cantPress[i] = true;
        justPressed[i] = false;
      }

      if (!cantPress[i] && keys[i]) {
        justPressed[i] = true;
      }
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() < 0 || e.getKeyCode() >= keys.length)
      return;
    keys[e.getKeyCode()] = true;
    System.out.println("PRESSED: " + e.getKeyCode());
  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (e.getKeyCode() < 0 || e.getKeyCode() >= keys.length)
      return;
    keys[e.getKeyCode()] = false;
  }

  public boolean keyJustPressed(int keyCode) {
    if (keyCode < 0 || keyCode >= keys.length)
      return false;
    return justPressed[keyCode];
  }

  public boolean isKeyPressed(int keyCode) {
    if (keyCode < 0 || keyCode >= keys.length)
      return false;
    return keys[keyCode];
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }
}