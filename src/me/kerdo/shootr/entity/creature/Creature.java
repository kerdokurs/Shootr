package me.kerdo.shootr.entity.creature;

import me.kerdo.shootr.Handler;
import me.kerdo.shootr.entity.Entity;
import me.kerdo.shootr.entity.creature.player.Player;
import me.kerdo.shootr.world.Tile;

public abstract class Creature extends Entity {
  public static final double DEFAULT_SPEED = 200;
  public static final int DEFAULT_WIDTH = 64,
          DEFAULT_HEIGHT = 64;

  protected double speed;
  protected double xMove, yMove;

  public Creature(final Handler handler, final double x, final double y, final int width, final int height, final int maxHealth) {
    super(handler, x, y, width, height, maxHealth);
    speed = DEFAULT_SPEED;
    xMove = 0;
    yMove = 0;
  }

  public void move(final double dt) {
    if (!checkEntityCollisions(xMove * dt, 0))
      moveX(dt);
    if (!checkEntityCollisions(0, yMove * dt))
      moveY(dt);
  }

  public void moveX(final double dt) {
    xMove *= dt;

    if (xMove > 0) {
      final int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILE_WIDTH;

      if (!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILE_HEIGHT) &&
                  !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILE_HEIGHT)) {
        x += xMove;
      } else {
        x = tx * Tile.TILE_WIDTH - bounds.x - bounds.width - 1;
        stopDash();
      }
    } else if (xMove < 0) {
      final int tx = (int) (x + xMove + bounds.x) / Tile.TILE_WIDTH;

      if (!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILE_HEIGHT) &&
                  !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILE_HEIGHT)) {
        x += xMove;
      } else {
        x = tx * Tile.TILE_WIDTH + Tile.TILE_WIDTH - bounds.x;
        stopDash();
      }
    }
  }

  public void moveY(final double dt) {
    yMove *= dt;

    if (yMove < 0) {
      final int ty = (int) (y + yMove + bounds.y) / Tile.TILE_HEIGHT;

      if (!collisionWithTile((int) (x + bounds.x) / Tile.TILE_WIDTH, ty) &&
                  !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILE_WIDTH, ty)) {
        y += yMove;
      } else {
        y = ty * Tile.TILE_HEIGHT + Tile.TILE_HEIGHT - bounds.y;
        stopDash();
      }
    } else if (yMove > 0) {
      final int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILE_HEIGHT;

      if (!collisionWithTile((int) (x + bounds.x) / Tile.TILE_WIDTH, ty) &&
                  !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILE_WIDTH, ty)) {
        y += yMove;
      } else {
        y = ty * Tile.TILE_HEIGHT - bounds.y - bounds.height - 1;
        stopDash();
      }
    }
  }

  private void stopDash() {
    if (this instanceof Player) {
      final Player e = (Player) this;

      if (e.isDashing())
        e.stopDash();
    }
  }

  protected boolean collisionWithTile(final int x, final int y) {
    return handler.getWorld().getTile(x, y).isSolid();
  }

  public double getxMov() {
    return xMove;
  }

  public void setxMov(final double xMov) {
    this.xMove = xMov;
  }

  public double getyMov() {
    return yMove;
  }

  public void setyMov(final double yMov) {
    this.yMove = yMov;
  }
}