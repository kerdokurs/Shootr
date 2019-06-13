package me.kerdo.shootr.menu;

import me.kerdo.shootr.Handler;

import java.awt.*;

import me.kerdo.shootr.entity.character.Character;
import me.kerdo.shootr.entity.character.Stats;
import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.gfx.Text;
import me.kerdo.shootr.gfx.ui.UIImage;
import me.kerdo.shootr.gfx.ui.UIImageButton;
import me.kerdo.shootr.gfx.ui.UIObject;

public class CharacterSelectionMenu extends Menu {
  private int selectedCharacterIndex = 0;
  private Character selectedCharacter = Character.CHARACTERS[selectedCharacterIndex];

  private final UIObject prevCharacterButton, nextCharacterButton, selectButton;

  public CharacterSelectionMenu(final Handler handler) {
    super(handler);

    prevCharacterButton = new UIImageButton(handler, 56, 364 - 24, 48, 48, object -> {
      selectedCharacterIndex--;

      if (selectedCharacterIndex < 0)
        selectedCharacterIndex = Character.CHARACTERS.length - 1;

      int frameIndex = selectedCharacter.getPortrait().getIndex();

      selectedCharacter = Character.CHARACTERS[selectedCharacterIndex];
      selectedCharacter.getPortrait().setIndex(frameIndex);
    }, Assets.arrowLeft, "Previous Character");

    nextCharacterButton = new UIImageButton(handler, 232, 364 - 24, 48, 48, object -> {
      selectedCharacterIndex++;

      if (selectedCharacterIndex >= Character.CHARACTERS.length)
        selectedCharacterIndex = 0;

      int frameIndex = selectedCharacter.getPortrait().getIndex();

      selectedCharacter = Character.CHARACTERS[selectedCharacterIndex];
      selectedCharacter.getPortrait().setIndex(frameIndex);
    }, Assets.arrowRight, "Next Character");

    selectButton = new UIImageButton(handler, handler.getWidth() - 68, handler.getHeight() - 68, 48, 48, object -> {
    }, Assets.arrowRight, "Select character");

    uiManager.addObject(prevCharacterButton);
    uiManager.addObject(nextCharacterButton);
    uiManager.addObject(selectButton);
  }

  @Override
  public void tick(final double dt) {
    selectedCharacter.getPortrait().tick(dt);
  }

  @Override
  public void render(final Graphics g) {
    g.drawImage(Assets.characterSelectionBackground, 0, 0, handler.getWidth(), handler.getHeight(), null);

    g.drawImage(selectedCharacter.getPortrait().get(0), 40, 40, 256, 256, null);
    g.drawImage(selectedCharacter.getPortrait().getCurrentFrame(), 168 - 96 / 2, 316, 96, 96, null);

    Text.drawString(g, selectedCharacter.getName(), 168, 432, true, Color.WHITE, Assets.andy32);

    Text.drawString(g, "Stats", 298, 96, false, Color.WHITE, Assets.andy64);

    Text.drawString(g, "Health: " + selectedCharacter.getStats().health, 328, 132, false, Color.WHITE, Assets.andy32);
    Text.drawString(g, "Movement speed: " + selectedCharacter.getStats().speed, 328, 162, false, Color.WHITE, Assets.andy32);
    Text.drawString(g, "Class: " + Stats.CLASSES[selectedCharacter.getStats().playClass], 328, 192, false, Color.WHITE, Assets.andy32);
  }
}
