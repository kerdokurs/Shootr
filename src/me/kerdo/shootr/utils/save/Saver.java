package me.kerdo.shootr.utils.save;

import java.util.ArrayList;

import com.google.gson.Gson;

import me.kerdo.shootr.entity.Entity;

public class Saver {
	public static String save(me.kerdo.shootr.entity.creature.Player player,
			ArrayList<me.kerdo.shootr.entity.Entity> entities, ArrayList<me.kerdo.shootr.portal.Portal> portals) {
		Gson gson = new Gson();
		Save save = new Save(portals.size());

		save.player = new Player(player.getX(), player.getY(), player.getHealth(), player.getLevelBar().getCurrentXP(),
				player.getCoins(), player.getUsername());

		int s = 0, v = 0, t = 0;
		for(Entity e : entities) {
			if(e instanceof me.kerdo.shootr.entity.statik.Tree) {
				s++;
			} else if(e instanceof me.kerdo.shootr.entity.statik.Post) {
				v++;
			} else if(e instanceof me.kerdo.shootr.entity.creature.Enemy) {
				t++;
			}
		}

		Entities ent = new Entities(s, v, t);

		for(Entity e : entities) {
			if(e instanceof me.kerdo.shootr.entity.statik.Tree) {
				ent.addTree(new Tree(e.getX(), e.getY(), e.getHealth()));
			} else if(e instanceof me.kerdo.shootr.entity.statik.Post) {
				ent.addPost(new Post(e.getX(), e.getY(), e.getHealth()));
			} else if(e instanceof me.kerdo.shootr.entity.creature.Enemy) {
				ent.addEnemy(new Enemy(e.getX(), e.getY(), e.getHealth()));
			}
		}

		for(int i = 0; i < portals.size(); i++) {
			me.kerdo.shootr.portal.Portal p = portals.get(i);
			save.addPortal(new Portal(p.getX(), p.getY(), p.getName()));
		}

		return gson.toJson(save);
	}
}

class Save {
	private transient int d = 0;
	public Entities entities;
	public Player player;
	public Portal[] portals;
	public Map map;

	public Save(int s) {
		portals = new Portal[s];
	}

	public void addPortal(Portal p) {
		portals[d] = p;
		d++;
	}
}

class Map {
	public String name;
	public boolean encryption;
	public int difficulty;
}

class Entities {
	private transient int a = 0, b = 0, c = 0;
	public Post[] posts;
	public Tree[] trees;
	public Enemy[] enemies;

	public Entities(int s, int v, int t) {
		posts = new Post[s];
		trees = new Tree[v];
		enemies = new Enemy[t];
	}

	public void addTree(Tree tree) {
		trees[a] = tree;
		a++;
	}

	public void addPost(Post post) {
		posts[b] = post;
		b++;
	}

	public void addEnemy(Enemy enemy) {
		enemies[c] = enemy;
		c++;
	}
}

class Player {
	public float x, y;
	public int health, xp, coins;
	public String username;

	public Player(float x, float y, int health, int xp, int coins, String username) {
		this.x = x;
		this.y = y;
		this.health = health;
		this.xp = xp;
		this.coins = coins;
		this.username = username;
	}
}

class Portal {
	public int x, y;
	public String name;

	public Portal(int x, int y, String name) {
		this.x = x;
		this.y = y;
		this.name = name;
	}
}

class Enemy {
	public float x, y;
	public int health;

	public Enemy(float x, float y, int health) {
		this.x = x;
		this.y = y;
		this.health = health;
	}
}

class Post {
	public float x, y;
	public int health;

	public Post(float x, float y, int health) {
		this.x = x;
		this.y = y;
		this.health = health;
	}
}

class Tree {
	public float x, y;
	public int health;

	public Tree(float x, float y, int health) {
		this.x = x;
		this.y = y;
		this.health = health;
	}
}