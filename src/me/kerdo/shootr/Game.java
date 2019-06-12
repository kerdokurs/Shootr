package me.kerdo.shootr;

import me.kerdo.shootr.entity.character.Character;
import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.gfx.Camera;
import me.kerdo.shootr.gfx.Display;
import me.kerdo.shootr.gfx.Text;
import me.kerdo.shootr.input.KeyManager;
import me.kerdo.shootr.input.MouseManager;
import me.kerdo.shootr.item.Item;
import me.kerdo.shootr.menu.MainMenu;
import me.kerdo.shootr.menu.MenuManager;
import me.kerdo.shootr.world.Tile;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

public class Game implements Runnable {
  // Thread and class handling
  private final Handler handler;
  private Thread thread;
  private boolean shouldClose = false;

  // Game loop
  /*private final int TARGET_FPS = 120;
  final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;*/

  // Use configuration file
  final int MAX_FPS = 240;
  final double PERIOD = 1 / MAX_FPS;

  final int RENDER_RATE = 10;
  final double RENDER_PERIOD = 1 / RENDER_RATE;
  final double RENDER_TIME = RENDER_PERIOD * 1000;

  // Window parameters
  private String title;
  private int width, height;

  // Mouse & keyboard
  private final KeyManager keyManager;
  private final MouseManager mouseManager;

  // Menu
  private final MenuManager menuManager;

  // Graphics
  private BufferStrategy bs;
  private Graphics g;
  private Display display;
  private Camera camera;

  // Misc
  private double fps;

  // Cursor
  private Toolkit toolkit = Toolkit.getDefaultToolkit();
  private Cursor cursor;

  public Game(final String title, final int width, final int height) {
    this.title = title;
    this.width = width;
    this.height = height;

    handler = new Handler(this);

    keyManager = new KeyManager();
    mouseManager = new MouseManager();
    menuManager = new MenuManager(handler, mouseManager);
  }

  private void init() {
    display = new Display(title, width, height);
    display.setListeners(keyManager, mouseManager);
    Assets.init();
    Tile.init();
    Item.init(handler);
    Character.init();

    cursor = toolkit.createCustomCursor(Assets.cursor, new Point(0, 0), "img");
    display.setCursor(cursor);

    camera = new Camera(handler, 0, 0);

    menuManager.setMenu(new MainMenu(handler));

    // handler.getGame().getMenuManager().getMenu().getUiManager().addObject(handler.getWorld().getPlayer().getInventory());
  }

  private void tick(final double dt) {
    keyManager.tick();

    if (menuManager.getMenu() != null) {
      menuManager.getMenu().tick(dt);
      menuManager.getMenu().getUiManager().tick(dt);
    }
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

    if (menuManager.getMenu() != null) {
      menuManager.getMenu().render(g);
      menuManager.getMenu().getUiManager().render(g);
    }

    if (keyManager.keyJustPressed(KeyEvent.VK_ESCAPE)) {
      shouldClose = true;
    }

    Text.drawString(g, "FPS: " + (int) fps, 15, 20, false, Color.WHITE, Assets.andy16);

    bs.show();
    g.dispose();
  }

  public void run() {
    init();

    /*long now, lastTime = System.nanoTime();
    double delta;

    final int RENDER_TIME = 60;
    int renderTimer = 0;

    while (!shouldClose) {
      // Delta time and framerate calculation
      now = System.nanoTime();
      delta = (now - lastTime) / 1e9;
      lastTime = now;
      fps = 1 / delta;

      // Updating and rendering
      tick(delta);

      // Rendering every renderTime updates
      renderTimer++;
      if (renderTimer == RENDER_TIME) {
        renderTimer = 0;
        render();
      }
    }*/

    /*long lastTime = System.nanoTime();
    long timer = System.currentTimeMillis();
    int frames = 0;

    while (!shouldClose) {
      final long now = System.nanoTime();
      final double dt = (now - lastTime) / (double) OPTIMAL_TIME;
      lastTime = now;

      frames++;

      if (System.currentTimeMillis() - timer > 1000) {
        timer += 1000;
        fps = frames;
        frames = 0;
      }

      tick(dt);
      render();
    }*/

    long lastTime = System.nanoTime(), now;
    long timer = System.currentTimeMillis();
    long renderTimer = System.currentTimeMillis();
    int frames = 0;

    while (!shouldClose) {
      now = System.nanoTime();
      final double dt = (now - lastTime) / 1e9;
      lastTime = now;

      tick(dt);

      if (System.currentTimeMillis() - renderTimer >= RENDER_TIME) {
        renderTimer += RENDER_TIME;
        render();
      }

      frames++;
      if (System.currentTimeMillis() - timer >= 1000) {
        timer += 1000;
        fps = frames;
        frames = 0;
      }

      try {
        Thread.sleep((long) (PERIOD - dt));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    stop();
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

    System.exit(1);
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

  public MenuManager getMenuManager() {
    return menuManager;
  }
}
