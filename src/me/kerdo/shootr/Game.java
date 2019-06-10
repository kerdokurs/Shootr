package me.kerdo.shootr;

import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.gfx.Camera;
import me.kerdo.shootr.gfx.Display;
import me.kerdo.shootr.gfx.Text;
import me.kerdo.shootr.input.KeyManager;
import me.kerdo.shootr.input.MouseManager;
import me.kerdo.shootr.item.Item;
import me.kerdo.shootr.menu.GameMenu;
import me.kerdo.shootr.menu.MenuManager;
import me.kerdo.shootr.world.Tile;

import java.awt.*;
import java.awt.image.BufferStrategy;

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

    cursor = toolkit.createCustomCursor(Assets.cursor, new Point(0, 0), "img");
    display.setCursor(cursor);

    camera = new Camera(handler, 0, 0);

    menuManager.setMenu(new GameMenu(handler));

    handler.getGame().getMenuManager().getMenu().getUiManager().addObject(handler.getWorld().getPlayer().getInventory());
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

    Text.drawString(g, "FPS: " + (int) fps, 15, 20, false, Color.WHITE, Assets.andy16);

    bs.show();
    g.dispose();
  }

  public void run() {
    init();

    long now, lastTime = System.nanoTime();
    double delta;

    final int RENDER_TIME = 50;
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

  public MenuManager getMenuManager() {
    return menuManager;
  }
}
