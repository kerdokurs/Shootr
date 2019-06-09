package me.kerdo.shootr.worlds;

import java.awt.Graphics;
import java.io.PrintWriter;

import me.kerdo.shootr.entity.creature.Enemy;
import me.kerdo.shootr.entity.creature.Player;
import me.kerdo.shootr.entity.statik.Post;
import me.kerdo.shootr.entity.statik.StaticEntity;
import me.kerdo.shootr.entity.statik.Tree;
import me.kerdo.shootr.items.ItemManager;
import me.kerdo.shootr.portal.Portal;
import me.kerdo.shootr.portal.PortalManager;
import me.kerdo.shootr.tile.Tile;
import me.kerdo.shootr.utils.Encryptor;
import me.kerdo.shootr.utils.Utils;
import me.kerdo.shootr.utils.handler.Handler;
import me.kerdo.shootr.utils.handler.SaveHandler;
import me.kerdo.shootr.utils.save.Saver;
import me.kerdo.shootr.weapons.BulletManager;
import me.kerdo.shootr.weapons.RangedWeaponManager;

public class World {
	private Handler handler;
	private int width, height;
	private int[][] tiles;

	private String save;
	private String map;
	private boolean mapEncryption;
	private int diff;

	private EntityManager entityManager;
	private BulletManager bulletManager;
	private RangedWeaponManager weaponManager;
	private ItemManager itemManager;

	private SaveHandler saveHandler;
	
	private PortalManager portalManager;

	private boolean rangeEnabled = false;
	private boolean inaccuracyEnabled = false;

	public World(Handler handler, String save) {
		this.handler = handler;
		entityManager = new EntityManager(handler, new Player(handler, 100, 100));
		saveHandler = new SaveHandler(handler, save);
		
		portalManager = new PortalManager();

		this.save = save;

		for(StaticEntity se : saveHandler.allEntities) {
			if(se instanceof Tree) {
				Tree tr = new Tree(handler, se.getX(), se.getY());
				tr.setHealth(se.getHealth());
				entityManager.addEntity(tr);
			} else if(se instanceof Post) {
				Post po = new Post(handler, se.getX(), se.getY());
				po.setHealth(se.getHealth());
				entityManager.addEntity(po);
			}
		}
		
		for(Enemy e : saveHandler.allEnemies) {
			Enemy ee = new Enemy(handler, e.getX(), e.getY());
			ee.setHealth(e.getHealth());
			entityManager.addEntity(ee);
		}
		
		for(Portal p : saveHandler.allPortals) {
			Portal portal = new Portal(handler, p.getName(), p.getX(), p.getY());
			portalManager.addPortal(portal);
		}

		float[] playerData = saveHandler.getPlayerData();
		System.out.println(playerData[0] + " : " + playerData[1] + " : " + playerData[2]);
		entityManager.getPlayer().setX(playerData[0]);
		entityManager.getPlayer().setY(playerData[1]);
		entityManager.getPlayer().setRespawnX(playerData[0]);
		entityManager.getPlayer().setRespawnY(playerData[1]);
		entityManager.getPlayer().setHealth((int) playerData[2]);
		entityManager.getPlayer().getLevelBar().setCurrentXp((int) playerData[3]);

		String path = saveHandler.getMapFile();
		map = path;
		boolean encrypted = saveHandler.getMapEncryption() || false;
		mapEncryption = encrypted;
		String username = saveHandler.getPlayerUsername();
		entityManager.getPlayer().setUsername(username);

		weaponManager = new RangedWeaponManager(handler);
		int diff = saveHandler.getDifficulty();
		this.diff = diff;
		if(diff == 0) {
			inaccuracyEnabled = false;
			rangeEnabled = false;
		} else if(diff == 1) {
			inaccuracyEnabled = true;
			rangeEnabled = false;
		} else if(diff == 2) {
			rangeEnabled = true;
			inaccuracyEnabled = false;
		} else if(diff == 3) {
			inaccuracyEnabled = true;
			rangeEnabled = true;
		}

		// entityManager.addEntity(new Tree(handler, 300, 400));
		// entityManager.addEntity(new Post(handler, 100, 400));
		// entityManager.addEntity(new Tree(handler, 400, 500));
		// entityManager.addEntity(new Post(handler, 600, 300));
		// entityManager.addEntity(new Enemy(handler, 800, 600));
		// entityManager.addEntity(new Enemy(handler, 700, 700));
		// entityManager.addEntity(new Enemy(handler, 200, 800));
		// entityManager.addEntity(new Enemy(handler, 600, 900));

		bulletManager = new BulletManager(handler);
		itemManager = new ItemManager(handler);
//		portalManager.addPortal(new Portal(handler, "Portal1", 100, 100));
//		portalManager.addPortal(new Portal(handler, "Portal2", 1000, 800));
//		
//		portalManager.addPortal(new OrangePortal(handler, "Portal1O", 500, 600, 40, 64));
//		portalManager.addPortal(new OrangePortal(handler, "Portal2O", 400, 500, 40, 64));
		
		//TODO: TEMP
//		entityManager.addEntity(new Enemy(handler, 400, 400));

		// vehicleManager.addVehicle(new Vehicle(handler, 0, Assets.car,
		// Vehicle.DEFAULT_SPEED, 400, 400, 0));

		loadWorld(path, encrypted);
	}

	public void tick() {
		entityManager.tick();
		bulletManager.tick();
		weaponManager.tick();
		itemManager.tick();
		portalManager.tick();
	}

	public void render(Graphics g) {
		int xStart = (int) Math.max(0, handler.getCamera().getxOff() / Tile.TILE_WIDTH);
		int xEnd = (int) Math.min(width, (handler.getCamera().getxOff() + handler.getWidth()) / Tile.TILE_WIDTH + 1);
		int yStart = (int) Math.max(0, handler.getCamera().getyOff() / Tile.TILE_HEIGHT);
		int yEnd = (int) Math.min(height, (handler.getCamera().getyOff() + handler.getHeight()) / Tile.TILE_WIDTH + 1);

		for(int x = xStart; x < xEnd; x++) {
			for(int y = yStart; y < yEnd; y++) {
				getTile(x, y).render(g, (int) (x * Tile.TILE_WIDTH - handler.getCamera().getxOff()),
						(int) (y * Tile.TILE_HEIGHT - handler.getCamera().getyOff()));
			}
		}

		itemManager.render(g);
		portalManager.render(g);
		entityManager.render(g);
		bulletManager.render(g);
		weaponManager.render(g);
	}

	public Tile getTile(int x, int y) {
		if(x < 0 || x >= width || y < 0 || y >= height)
			return Tile.errorTile;

		Tile t = Tile.tiles[tiles[x][y]];
		if(t == null)
			return Tile.errorTile;
		return t;
	}

	public String saveSave() {
		PrintWriter writer;
		try {
			writer = new PrintWriter(Utils.DIR + "custom/c.json", "UTF-8");
			writer.write(Saver.save(entityManager.getPlayer(), entityManager.getEntities(), portalManager.getPortals()));
			writer.close();
		} catch(Exception e1) {
			e1.printStackTrace();
		}
		return "done";
//		return saveHandler.saveSave(save, entityManager.getEntities(), portalManager.getPortals(), entityManager.getPlayer().getX(),
//				entityManager.getPlayer().getY(), entityManager.getPlayer().getHealth(),
//				entityManager.getPlayer().getUsername(), entityManager.getPlayer().getLevelBar().getCurrentXP(), map, mapEncryption, diff);
	}

	private void loadWorld(String path, boolean encrypted) {
		String file = Utils.loadFileAsStringFromDisk("/maps/" + path + ".txt");
		if(encrypted) {
			file = Encryptor.Decrypt(Encryptor.DEFAULT_KEY, Encryptor.DEFAULT_INITVECTOR, file);
		}
		// String encrypted = Encryptor.Encrypt(Encryptor.DEFAULT_KEY,
		// Encryptor.DEFAULT_INITVECTOR, file);
		// try {
		// final BufferedWriter writer =
		// Files.newBufferedWriter(Paths.get(Utils.DIR + "/maps/dev.txt"),
		// StandardCharsets.UTF_8, StandardOpenOption.CREATE);
		// writer.write(encrypted);
		// writer.close();
		// } catch(IOException e) {
		// e.printStackTrace();
		// }

		String[] tokens = file.split("\\s+");
		width = Utils.parseInt(tokens[0]);
		height = Utils.parseInt(tokens[1]);

		tiles = new int[width][height];
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				tiles[x][y] = Utils.parseInt(tokens[(x + y * width) + 2]);
			}
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public BulletManager getBulletManager() {
		return bulletManager;
	}

	public RangedWeaponManager getWeaponManager() {
		return weaponManager;
	}

	public ItemManager getItemManager() {
		return itemManager;
	}

	public boolean getRangeEnabled() {
		return rangeEnabled;
	}

	public void setRangeEnabled(boolean rangeEnabled) {
		this.rangeEnabled = rangeEnabled;
	}

	public boolean getInaccuracyEnabled() {
		return inaccuracyEnabled;
	}

	public void setInaccuracyEnabled(boolean inaccuracyEnabled) {
		this.inaccuracyEnabled = inaccuracyEnabled;
	}
	
	public PortalManager getPortalManager() {
		return portalManager;
	}
	
	public void setPortalmanager(PortalManager portalManager) {
		this.portalManager = portalManager;
	}
}