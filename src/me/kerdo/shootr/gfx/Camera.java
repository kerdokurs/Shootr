package me.kerdo.shootr.gfx;

import me.kerdo.shootr.Handler;
import me.kerdo.shootr.entity.Entity;
import me.kerdo.shootr.world.Tile;

public class Camera {
  private Handler handler;
  private float xOff, yOff;

  public Camera(final Handler handler, final float xOff, final float yOff) {
    this.handler = handler;
    this.xOff = xOff;
    this.yOff = yOff;
  }

  public void checkBlank() {
    if (handler.getCamera() == null)
      return;

    if (xOff < 0) {
      xOff = 0;
    } else if (xOff > handler.getWorld().getWidth() * Tile.TILE_WIDTH - handler.getWidth()) {
      xOff = handler.getWorld().getWidth() * Tile.TILE_WIDTH - handler.getWidth();
    }

    if (yOff < 0) {
      yOff = 0;
    } else if (yOff > handler.getWorld().getHeight() * Tile.TILE_HEIGHT - handler.getHeight()) {
      yOff = handler.getWorld().getHeight() * Tile.TILE_HEIGHT - handler.getHeight();
    }
  }
	
	public void centerOnEntity(final Entity e) {
		xOff = e.getX() - handler.getWidth() / 2 + e.getWidth() / 2;
		yOff = e.getY() - handler.getHeight() / 2 + e.getHeight() / 2;
		checkBlank();
	}

  public void move(float xAmt, float yAmt) {
    xOff += xAmt;
    yOff += yAmt;
    checkBlank();
  }

  public float getxOff() {
    return xOff;
  }

  public void setxOff(float xOff) {
    this.xOff = xOff;
    checkBlank();
  }

  public float getyOff() {
    return yOff;
  }

  public void setyOff(float yOff) {
    this.yOff = yOff;
    checkBlank();
  }
}