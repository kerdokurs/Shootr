package me.kerdo.shootr.gfx;

import java.awt.image.BufferedImage;

public class Animation {
  private int speed, index;
  private long lastTime, timer;
  private final BufferedImage[] frames;

  public Animation(final BufferedImage[] frames, final int speed) {
    this.speed = speed;
    this.frames = frames;
    index = 0;
    timer = 0;
    lastTime = System.currentTimeMillis();
  }

  public void tick(final double dt) {
    timer += System.currentTimeMillis() - lastTime;
    lastTime = System.currentTimeMillis();

    if (timer > speed) {
      index++;
      timer = 0;
      if (index >= frames.length)
        index = 0;
    }
  }

  public BufferedImage getCurrentFrame() {
    return frames[index];
  }

  public BufferedImage get(final int i) {
    return frames[i];
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(final int index) {
    this.index = index;
  }
}