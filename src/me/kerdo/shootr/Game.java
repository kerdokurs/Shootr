package me.kerdo.shootr;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import me.kerdo.shootr.display.Display;
import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.gfx.Camera;
import me.kerdo.shootr.gfx.Text;
import me.kerdo.shootr.input.KeyManager;
import me.kerdo.shootr.input.MouseManager;
import me.kerdo.shootr.menu.CreditsMenu;
import me.kerdo.shootr.menu.LoadMenu;
import me.kerdo.shootr.menu.MainMenu;
import me.kerdo.shootr.menu.Menu;
import me.kerdo.shootr.menu.MenuManager;
import me.kerdo.shootr.menu.NewMenu;
import me.kerdo.shootr.menu.PauseMenu;
import me.kerdo.shootr.menu.SettingsMenu;
import me.kerdo.shootr.net.Client;
import me.kerdo.shootr.utils.handler.Handler;

/**
 *
 * @author Kerdo
 *
 */
public class Game implements Runnable {
    public static Menu mainmenu, creditsMenu, loadMenu, newMenu, pauseMenu, settingsMenu;

    private Display display;
    private Thread thread;

    private int width, height;
    public String title;
    private int fps = 60;

    private BufferStrategy bs;
    private Graphics g;

    private KeyManager keyManager;
    private MouseManager mouseManager;

    private Camera camera;
    private Handler handler;

    private Client client;

    private boolean running = false;

    private int tt = 0;

    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private Cursor cursor;

    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;

        keyManager = new KeyManager();
        mouseManager = new MouseManager();
    }

    private void init() {
        display = new Display(title, width, height);
        display.setListeners(keyManager, mouseManager);
        Assets.init();

        cursor = toolkit.createCustomCursor(Assets.cursor, new Point(0, 0), "img");
        display.setCursor(cursor);

        handler = new Handler(this);
        camera = new Camera(handler, 0, 0);

        mainmenu = new MainMenu(handler);
        creditsMenu = new CreditsMenu(handler);
        loadMenu = new LoadMenu(handler);
        newMenu = new NewMenu(handler);
        pauseMenu = new PauseMenu(handler);
        settingsMenu = new SettingsMenu(handler);
        MenuManager.setMenu(mainmenu);
    }

    private void tick() {
        keyManager.tick();
        if(MenuManager.getMenu() != null)
            MenuManager.getMenu().tick();
    }

    private void render() {
        bs = display.getCanvas().getBufferStrategy();
        if(bs == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }

        g = bs.getDrawGraphics();
        g.clearRect(0, 0, width, height);

        if(MenuManager.getMenu() != null)
            MenuManager.getMenu().render(g);

        Text.drawString(g, "fps: " + tt, 15, 20, false, Color.WHITE, Assets.roboto16);

        bs.show();
        g.dispose();
    }

    public void run() {
        init();

        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;

        while(running) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;

            if(delta >= 1) {
                tick();
                render();
                ticks++;
                delta--;
            }

            if(timer >= 1000000000) {
                System.out.println(ticks);
                tt = ticks;
                ticks = 0;
                timer = 0;
            }
        }

        stop();
    }

    public MouseManager getMouseManager() {
        return mouseManager;
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }

    public Camera getCamera() {
        return camera;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getFps() {
        return fps;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public synchronized void start() {
        if(running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        if(!running)
            return;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}