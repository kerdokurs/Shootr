package me.kerdo.shootr.entity;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import me.kerdo.shootr.entity.creature.Player;
import me.kerdo.shootr.entity.creature.PlayerMP;
import me.kerdo.shootr.entity.statik.Post;
import me.kerdo.shootr.entity.statik.Tree;
import me.kerdo.shootr.utils.handler.Handler;

public class EntityManager {
	private Handler handler;
	private Player player;
	private ArrayList<Entity> entities;
	private ArrayList<PlayerMP> mpPlayers;
	private Comparator<Entity> comp = new Comparator<Entity>() {
		@Override
		public int compare(Entity a, Entity b) {
			if(a.getY() + a.getHeight() < b.getY() + b.getHeight())
				return -1;
			else
				return 1;
		}
	};

	public EntityManager(Handler handler, Player player) {
		this.handler = handler;
		this.player = player;
		entities = new ArrayList<Entity>();
		entities.add(player);
		mpPlayers = new ArrayList<PlayerMP>();
	}

	public void tick() {
		try {
			Iterator<Entity> it = entities.iterator();
			while(it.hasNext()) {
				Entity e = it.next();
				int w = e.getWidth();
				if(e instanceof Player) {
				} else if(e instanceof PlayerMP) {
				} else {
					if(e instanceof Tree || e instanceof Post)
						w = w * 2;
					if(e.getY() + w < handler.getCamera().getyOff() || e.getY() > handler.getHeight() + handler.getCamera().getyOff() ||
					   e.getX() + w < handler.getCamera().getxOff() || e.getX() > handler.getWidth() + handler.getCamera().getxOff()) {
						continue;
					}
				}
				e.tick();
//				if(e instanceof Player) {
//				} else {
					if(!e.isActive())
						it.remove();
//				}
			}
			entities.sort(comp);
		} catch(ConcurrentModificationException e) {
			return;
		}
	}

	public void render(Graphics g) {
		try {
			for(Entity e : entities) {
				int w = e.getWidth();
				if(e instanceof Player) {
				} else if(e instanceof PlayerMP) {
				} else {
					if(e instanceof Tree || e instanceof Post)
						w = w * 2;
					if(e.getY() + w < handler.getCamera().getyOff() || e.getY() > handler.getHeight() + handler.getCamera().getyOff() ||
					   e.getX() + w < handler.getCamera().getxOff() || e.getX() > handler.getWidth() + handler.getCamera().getxOff()) {
						continue;
					}
				}
				e.render(g);
				e.displayHealth(g);
			}
		} catch(ConcurrentModificationException e) {
			return;
		}

		player.postRender(g);
	}

	public void addEntity(Entity e) {
		if(e instanceof PlayerMP) {
			mpPlayers.add((PlayerMP) e); //TODO: MIGHT NOT WORK
		}
		entities.add(e);
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}

	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
	}

	public ArrayList<PlayerMP> getMpPlayers() {
		return mpPlayers;
	}

	public void setMpPlayers(ArrayList<PlayerMP> mpPlayers) {
		this.mpPlayers = mpPlayers;
	}

}