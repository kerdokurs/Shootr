package me.kerdo.shootr.menu;

import me.kerdo.shootr.Handler;
import me.kerdo.shootr.entity.character.Character;
import me.kerdo.shootr.entity.creature.Player;
import me.kerdo.shootr.world.World;

import java.awt.*;

public class GameMenu extends Menu {
  private World world;

  public GameMenu(final Handler handler) {
    super(handler);
    world = new World(handler);
    handler.setWorld(world);

    world.getEntityManager().addEntity(new Player(handler, 200, 200, Character.CHARACTERS[0]));
    uiManager.addObject(handler.getWorld().getPlayer().getInventory());
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
