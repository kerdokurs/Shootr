package me.kerdo.shootr.menu;

import me.kerdo.shootr.Handler;
import me.kerdo.shootr.gfx.Animation;
import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.gfx.ui.UIAnimation;
import me.kerdo.shootr.gfx.ui.UIObject;
import me.kerdo.shootr.gfx.ui.UITextButton;

import java.awt.*;

public class MainMenu extends Menu {
  public MainMenu(final Handler handler) {
    super(handler);

    final UIObject newButton = new UITextButton(handler, handler.getWidth() / 2 + 80, 354, 51, 36,
            obj -> handler.getGame().getMenuManager().setMenu(new CharacterSelectionMenu(handler)),
            "New", new Color[] { Color.WHITE, Color.BLACK }, Assets.andy32);
    newButton.setVisible(false);

    final UIObject loadButton = new UITextButton(handler, handler.getWidth() / 2 + 80, 400, 58, 36,
            obj -> handler.getGame().getMenuManager().setMenu(new GameMenu(handler)),
            "Load", new Color[] { Color.WHITE, Color.BLACK }, Assets.andy32);
    loadButton.setVisible(false);

    final UIObject multiplayerButton = new UITextButton(handler, handler.getWidth() / 2 + 80, 446, 133, 36,
            obj -> handler.getGame().getMenuManager().setMenu(new MultiplayerMenu(handler)),
            "Multiplayer", new Color[] { Color.WHITE, Color.BLACK }, Assets.andy32);
    multiplayerButton.setVisible(false);

    final UIObject forwardArrow = new UIAnimation(handler, handler.getWidth() / 2 + 52, 416, 32, 32, new Animation(Assets.arrowRight, 260), true);
    forwardArrow.setVisible(false);

    uiManager.addObject(newButton);
    uiManager.addObject(loadButton);
    uiManager.addObject(multiplayerButton);
    uiManager.addObject(forwardArrow);

    final UIObject playButton = new UITextButton(handler, handler.getWidth() / 2 - 24, 400, 48, 36, obj -> {
      newButton.setVisible(!newButton.isVisible());
      loadButton.setVisible(!loadButton.isVisible());
      multiplayerButton.setVisible(!multiplayerButton.isVisible());
      forwardArrow.setVisible(!forwardArrow.isVisible());
    }, "Play", new Color[] { Color.WHITE, Color.BLACK }, Assets.andy32);

    final UIObject settingsButton = new UITextButton(handler, handler.getWidth() / 2 - 49, 446, 97, 36, obj -> {
      handler.getGame().getMenuManager().setMenu(new SettingsMenu(handler));
    }, "Settings", new Color[] { Color.WHITE, Color.BLACK }, Assets.andy32);

    final UIObject exitButton = new UITextButton(handler, handler.getWidth() / 2 - 24, 492, 48, 36, obj -> System.exit(1), "Exit", new Color[] { Color.WHITE, Color.BLACK }, Assets.andy32);

    uiManager.addObject(playButton);
    uiManager.addObject(settingsButton);
    uiManager.addObject(exitButton);
  }

  @Override
  public void tick(final double dt) {
  }

  @Override
  public void render(final Graphics g) {
    g.setColor(Color.LIGHT_GRAY);
    g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
  }
}
