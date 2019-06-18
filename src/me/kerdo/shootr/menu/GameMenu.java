package me.kerdo.shootr.menu;

import com.google.gson.Gson;
import me.kerdo.shootr.Handler;
import me.kerdo.shootr.entity.creature.Enemy;
import me.kerdo.shootr.entity.creature.player.Player;
import me.kerdo.shootr.utils.Utils;
import me.kerdo.shootr.utils.save.SaveObject;
import me.kerdo.shootr.world.World;

import java.awt.*;

public class GameMenu extends Menu {
  private World world;

  public GameMenu(final Handler handler) {
    super(handler);
    world = new World(handler);
    handler.setWorld(world);

    final Gson gson = new Gson();

    final String data = Utils.loadFileFromDisk(System.getProperty("user.home") + "\\data.json");

    final SaveObject saveObject = gson.fromJson(data, SaveObject.class);

    world.getEntityManager().setPlayer(new Player(handler, saveObject.player));

    // TEMP
    handler.getWorld().getEntityManager().addEntity(new Enemy(handler, 300, 300, 32, 32, 400));
    handler.getWorld().getEntityManager().addEntity(new Enemy(handler, 400, 300, 32, 32, 400));
    handler.getWorld().getEntityManager().addEntity(new Enemy(handler, 300, 400, 32, 32, 400));
    handler.getWorld().getEntityManager().addEntity(new Enemy(handler, 400, 400, 32, 32, 400));
  }

  @Override
  public void tick(final double dt) {
    world.tick(dt);
  }

  @Override
  public void render(final Graphics g) {
    world.render(g);
  }
}
