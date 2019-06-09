package me.kerdo.shootr;

import me.kerdo.shootr.gfx.*;
import me.kerdo.shootr.gfx.ui.*;
import me.kerdo.shootr.input.KeyManager;
import me.kerdo.shootr.input.MouseManager;
import me.kerdo.shootr.menu.GameMenu;
import me.kerdo.shootr.menu.MainMenu;
import me.kerdo.shootr.menu.Menu;
import me.kerdo.shootr.menu.MenuManager;
import me.kerdo.shootr.world.Tile;
import me.kerdo.shootr.world.World;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game implements Runnable {
  // Thread and class handling
  private final Handler handler;
  private Thread thread;
  private boolean shouldClose = false;

  // Window parameters
  private String title;
  private int width, height;

  // Mouse & keyboard
  private final KeyManager keyManager;
  private final MouseManager mouseManager;

  // UI
  private UIManager uiManager;

  // Menu
  private final MenuManager menuManager = new MenuManager();

  // Graphics
  private BufferStrategy bs;
  private Graphics g;
  private Display display;
  private Camera camera;

  // Misc
  private double fps;

  public Game(final String title, final int width, final int height) {
    this.title = title;
    this.width = width;
    this.height = height;

    handler = new Handler(this);

    keyManager = new KeyManager();
    mouseManager = new MouseManager();
  }

  private void init() {
    display = new Display(title, width, height);
    display.setListeners(keyManager, mouseManager);
    Assets.init();
    Tile.init();

    // TODO: Add custom cursor

    camera = new Camera(handler, 0, 0);

    // TODO: Add separate UIManager for each menu
    uiManager = new UIManager();

    // UI initialization

    mouseManager.setUiManager(uiManager);

    menuManager.setMenu(new GameMenu(handler));
  }

  private void tick(final double dt) {
    keyManager.tick();

    if (menuManager.getMenu() != null)
      menuManager.getMenu().tick(dt);

    uiManager.tick(dt);
  }

  private void render() {
    bs = display.getCanvas().getBufferStrategy();

    if (bs == null) {
      display.getCanvas().createBufferStrategy(3);
      return;
    }

    g = bs.getDrawGraphics();
    g.clearRect(0, 0, width, height);

    // Rendering

    if (menuManager.getMenu() != null)
      menuManager.getMenu().render(g);

    Text.drawString(g, "FPS: " + (int) fps, 15, 20, false, Color.WHITE, Assets.andy16);

    uiManager.render(g);

    bs.show();
    g.dispose();
  }

  public void run() {
    init();

    long now, lastTime = System.nanoTime();
    double delta;

    while (!shouldClose) {
      // Delta time and framerate calculation
      now = System.nanoTime();
      delta = (now - lastTime) / 1e9;
      lastTime = now;
      fps = 1 / delta;

      // Updating and rendering
      tick(delta);
      render();
    }
  }

  public synchronized void start() {
    if (shouldClose)
      return;

    shouldClose = false;
    thread = new Thread(this);
    thread.start();
  }

  public synchronized void stop() {
    if (!shouldClose)
      return;

    try {
      thread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public Camera getCamera() {
    return camera;
  }

  public KeyManager getKeyManager() {
    return keyManager;
  }

  public MouseManager getMouseManager() {
    return mouseManager;
  }
}
