package me.kerdo.shootr.weapons;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

import me.kerdo.shootr.entity.Entity;
import me.kerdo.shootr.entity.creature.Player;
import me.kerdo.shootr.entity.creature.PlayerMP;
import me.kerdo.shootr.tile.Tile;
import me.kerdo.shootr.utils.handler.Handler;

public class BulletManager {
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private Handler handler;

	public BulletManager(Handler handler) {
		this.handler = handler;
	}

	public void tick() {
		Iterator<Bullet> it = bullets.iterator();
		while(it.hasNext()) {
			Bullet b = it.next();
			boolean remove = b.tick();
			if(remove) {
				it.remove();
			}
		}
	}

	public void bulletCollision() {
		Iterator<Bullet> it = bullets.iterator();
		while(it.hasNext()) {
			Bullet b = it.next();
			double bx = b.getX();
			double by = b.getY();
			double br = b.getR();
			if(handler.getWorld().getEntityManager().getPlayer().isMultiplayer()) {
				for(int j = 0; j < handler.getWorld().getEntityManager().getEntities().size(); j++) {
					Entity e = handler.getWorld().getEntityManager().getEntities().get(j);
					if(e instanceof PlayerMP) {
						for(PlayerMP pmp : handler.getWorld().getEntityManager().getMpPlayers()) {
							if(!pmp.getUsername().equals(b.getShooter())) {
								Rectangle bb = new Rectangle((int) (pmp.getX()), (int) (pmp.getY()),
										(int) pmp.getWidth(), (int) pmp.getHeight());
								Rectangle bp = new Rectangle((int) bx, (int) by, (int) br, (int) br);
								if(bp.intersects(bb)) {
									pmp.hurt((int) b.getDamage());
									it.remove();
									break;
								}
							}
						}
					} else {
						if(e instanceof Player) {
							if(b.getShooter().equals(handler.getWorld().getEntityManager().getPlayer().getUsername())) {
								continue;
							}
						}

						Rectangle bb = new Rectangle((int) (e.getX()), (int) (e.getY()), (int) e.getWidth(),
								(int) e.getHeight());
						Rectangle bp = new Rectangle((int) bx, (int) by, (int) br, (int) br);
						if(bp.intersects(bb)) {
							e.hurt((int) b.getDamage());
							it.remove();
							break;
						}
					}
				}
			} else {
				for(int j = 0; j < handler.getWorld().getEntityManager().getEntities().size(); j++) {
					Entity e = handler.getWorld().getEntityManager().getEntities().get(j);
					if(e == handler.getWorld().getEntityManager().getPlayer())
						continue;
					
					if(e.getY() + e.getWidth() < handler.getCamera().getyOff() || e.getY() > handler.getHeight() + handler.getCamera().getyOff() ||
					   e.getX() + e.getWidth() < handler.getCamera().getxOff() || e.getX() > handler.getWidth() + handler.getCamera().getxOff()) {
						continue;
					}

					Rectangle bb = new Rectangle((int) (e.getX()), (int) (e.getY()), (int) e.getWidth(),
							(int) e.getHeight());
					Rectangle bp = new Rectangle((int) bx, (int) by, (int) br, (int) br);
					if(bp.intersects(bb)) {
						e.hurt((int) b.getDamage());
						it.remove();
						break;
					}
				}
			}
			
			if(handler.getWorld().getTile((int) (bx / Tile.TILE_WIDTH), (int) (by / Tile.TILE_HEIGHT)).isSolid()) {
				it.remove();
			}
		}

	}

	public void render(Graphics g) {
		Iterator<Bullet> it = bullets.iterator();
		while(it.hasNext()) {
			Bullet b = it.next();
			b.render(g);
		}
	}

	public void addBullet(Bullet b) {
		bullets.add(b);
	}

	public void removeBullet(Bullet b) {
		bullets.remove(b);
	}

	public ArrayList<Bullet> getBullets() {
		return bullets;
	}
}