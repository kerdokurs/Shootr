package me.kerdo.shootr.entity.creature;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.google.gson.Gson;

import me.kerdo.shootr.net.kPacket;
import me.kerdo.shootr.Game;
import me.kerdo.shootr.audio.Audio;
import me.kerdo.shootr.chat.Chat;
import me.kerdo.shootr.gfx.Animation;
import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.gfx.Dialog;
import me.kerdo.shootr.gfx.Text;
import me.kerdo.shootr.gfx.bar.Ammobar;
import me.kerdo.shootr.gfx.bar.Healthbar;
import me.kerdo.shootr.gfx.bar.Levelbar;
import me.kerdo.shootr.inventory.Inventory;
import me.kerdo.shootr.menu.Menu;
import me.kerdo.shootr.menu.MenuManager;
import me.kerdo.shootr.portal.Portal;
import me.kerdo.shootr.tile.Tile;
import me.kerdo.shootr.utils.Controls;
import me.kerdo.shootr.utils.Utils;
import me.kerdo.shootr.utils.handler.Handler;
import me.kerdo.shootr.weapons.Bullet;
import me.kerdo.shootr.weapons.RangedWeapon;
import me.kerdo.shootr.weapons.RangedWeaponManager;

public class Player extends Creature {
	private Animation animDown;

	private RangedWeaponManager weaponManager;

	private RangedWeapon currentWeapon;
	public int currentWeaponIndex = 0;
	private RangedWeapon[] weapons;
	private float inaccuracy;

	public Inventory inventory;
	public Chat chat;

	private final float DEFAULT_SPEED = 4f;

	private String username;

	private long firingTimer, firingDelay;
	private long reloadTime;
	private boolean reloading = false;

	private Gson gson;

	private boolean multiplayer = false;
	private boolean dev = false;

	private boolean died = false;
	private int respawnTime = 120, diedTimer = respawnTime;
	private float respawnX, respawnY;

	private boolean showPortalMenu = false, doPortalMessage = false;
	private int currentSelectedPortal = 0, portalMessageTime = 0, maxPortalMessagetime = 180;
	private Color portalMenuColor;
	private Portal currentPortal;
	private ArrayList<Portal> portals;
	private String defaultPortalError = "Unexpected exception has occurred", portalError = defaultPortalError;

	private Healthbar hb;
	private Ammobar ab;
	private Levelbar lb;

	private Animation coinAnim;
	private int coins = 0;

	public Player(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_WIDTH, Creature.DEFAULT_HEIGHT, 100);
		Controls.init();
		Dialog.init();

		respawnX = x;
		respawnY = y;

		bounds.x = 16;
		bounds.y = 32;
		bounds.width = 32;
		bounds.height = 32;

		firingTimer = System.nanoTime();
		firingDelay = RangedWeapon.DEFAULT_SHOOT_TIME;
		reloadTime = RangedWeapon.DEFAULT_RELOAD_TIME;

		speed = DEFAULT_SPEED;

		animDown = new Animation(55, Assets.player);

		coinAnim = new Animation(200, Assets.coin);

		gson = new Gson();

		weaponManager = new RangedWeaponManager(handler);

		weapons = new RangedWeapon[16];
		weapons[0] = RangedWeapon.pistol;
		weapons[1] = RangedWeapon.shotgun;
		weapons[2] = RangedWeapon.rifle;
		weapons[3] = RangedWeapon.minigun;
		weapons[4] = RangedWeapon.sniper;
		currentWeapon = weapons[0];

		inaccuracy = currentWeapon.getInaccuracy();

		inventory = new Inventory(handler);
		chat = new Chat(handler, 12, 425);

		hb = new Healthbar(this, handler.getWidth() - 260, handler.getHeight() - 60, 250, 50,
				new Color(255, 27, 27, 255));
		lb = new Levelbar(handler, handler.getWidth() - 260, handler.getHeight() - 120, 250, 50, Color.YELLOW);
		ab = new Ammobar(this, handler.getWidth() - 260, handler.getHeight() - 180, 250, 50,
				new Color(27, 255, 27, 255));
	}

	@Override
	public void tick() {
		if(!died) {
			animDown.tick();
			getInput();
			move();
			getControls();
			checkPortalCollision();
			handler.getCamera().centerOnEntity(this);

			if(currentWeapon != null)
				handler.getWorld().getBulletManager().bulletCollision();

			long elapsed = (System.nanoTime() - firingTimer) / 1000000;
			if(elapsed > firingDelay) {
				if(reloading) {
					currentWeapon.setBullets(currentWeapon.getClipSize());
					reloading = false;
				}
			}

			if(multiplayer)
				chat.tick();
			inventory.tick();
		} else {
			diedTimer--;
			if(diedTimer <= 0) {
				died = false;
				diedTimer = respawnTime;
				health = 100;
			}
		}

		hb.tick();
		ab.tick();
		lb.tick();
		coinAnim.tick();
	}

	public void attack() {
		if(died)
			return;
		if(currentWeapon == null)
			return;
		if(reloading)
			return;
		long elapsed = (System.nanoTime() - firingTimer) / 1000000;
		if(currentWeapon instanceof RangedWeapon) {
			if(elapsed > firingDelay) {
				if(reloading)
					reloading = false;
				firingDelay = currentWeapon.getShootTime();
				int mx = handler.getMouseManager().getMouseX();
				int my = handler.getMouseManager().getMouseY();
				int xx = (int) (x + width / 2 - handler.getCamera().getxOff());
				int yy = (int) (y + height / 2 - handler.getCamera().getyOff());
				float angle = (float) Math.atan2(my - yy, mx - xx);
				int size = 4;
				double speed = currentWeapon.getBulletspeed();
				float bx = x + width / 2 - size;
				float by = y + height / 2 - size;
				Audio.playSound("gunshot.wav", -17.5f * 100 / Controls.VOLUME);
				if(handler.getWorld().getInaccuracyEnabled()) {
					float rand = (float) (Math.random() * 2);
					float neg = Math.round(Math.random() * 1);
					if(neg == 1)
						inaccuracy = inaccuracy * -rand;
					else
						inaccuracy = inaccuracy * rand;
				} else {
					inaccuracy = 0;
				}
				float rAngle = angle + inaccuracy;
				if(currentWeapon == weapons[1]) {
					handler.getWorld().getBulletManager().addBullet(new Bullet(handler, angle, bx, by, speed, size,
							Color.WHITE, currentWeapon.getDamage(), username, currentWeapon.getMaxBulletTravelTime()));
					handler.getWorld().getBulletManager().addBullet(new Bullet(handler, angle - .1, bx, by, speed, size,
							Color.WHITE, currentWeapon.getDamage(), username, currentWeapon.getMaxBulletTravelTime()));
					handler.getWorld().getBulletManager().addBullet(new Bullet(handler, angle + .1, bx, by, speed, size,
							Color.WHITE, currentWeapon.getDamage(), username, currentWeapon.getMaxBulletTravelTime()));
					if(multiplayer) {
						kPacket sp = new kPacket("SHOOT", 8);
						sp.add(username);
						sp.add(Float.toString(bx));
						sp.add(Float.toString(by));
						sp.add(Integer.toString(size));
						sp.add(Double.toString(speed));
						sp.add(Float.toString(angle));
						sp.add(Float.toString(currentWeapon.getDamage()));
						sp.add(Float.toString(currentWeapon.getMaxBulletTravelTime()));

						handler.getGame().getClient().sendData(gson.toJson(sp).getBytes());

						sp.set(5, Float.toString(angle - .1f));
						handler.getGame().getClient().sendData(gson.toJson(sp).getBytes());

						sp.set(5, Float.toString(angle + .1f));
						handler.getGame().getClient().sendData(gson.toJson(sp).getBytes());

						// handler.getGame().client.sendData(("shoot:>/" +
						// username + "/" + bx + "/" + by + "/" + size + "/"
						// + speed + "/" + angle + "/" +
						// currentWeapon.getDamage() + "/").getBytes());
						// handler.getGame().client.sendData(("shoot:>/" +
						// username + "/" + bx + "/" + by + "/" + size + "/"
						// + speed + "/" + (angle - .1) + "/" +
						// currentWeapon.getDamage() + "/").getBytes());
						// handler.getGame().client.sendData(("shoot:>/" +
						// username + "/" + bx + "/" + by + "/" + size + "/"
						// + speed + "/" + (angle + .1) + "/" +
						// currentWeapon.getDamage() + "/").getBytes());
					}
				} else {
					handler.getWorld().getBulletManager().addBullet(new Bullet(handler, rAngle, bx, by, speed, size,
							Color.WHITE, currentWeapon.getDamage(), username, currentWeapon.getMaxBulletTravelTime()));
					kPacket sp = new kPacket("SHOOT", 8);
					sp.add(username);
					sp.add(Float.toString(bx));
					sp.add(Float.toString(by));
					sp.add(Integer.toString(size));
					sp.add(Double.toString(speed));
					sp.add(Float.toString(angle));
					sp.add(Float.toString(currentWeapon.getDamage()));
					sp.add(Float.toString(currentWeapon.getMaxBulletTravelTime()));

					if(multiplayer) {
						handler.getGame().getClient().sendData(gson.toJson(sp).getBytes());
					}

					// if(handler.getGame().client != null)
					// handler.getGame().client.sendData(("shoot:>/" + username
					// + "/" + bx + "/" + by + "/" + size + "/"
					// + speed + "/" + rAngle + "/" + currentWeapon.getDamage()
					// + "/").getBytes());
				}
				firingTimer = System.nanoTime();

				weaponManager.shoot(handler, currentWeapon);
				currentWeapon.setBullets(currentWeapon.getBullets() - 1);
				if(currentWeapon.getBullets() <= 0) {
					firingDelay = firingDelay + reloadTime;
					reloading = true;
				}
			}
		}
//		} else if(currentWeapon instanceof MeleeWeapon) {
//			
//		}
	}

	public void getControls() {
		if(died)
			return;
		if(showPortalMenu) {
			if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP)) {
				currentSelectedPortal--;
				if(currentSelectedPortal < 0)
					currentSelectedPortal = portals.size() - 1;
			} else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN)) {
				currentSelectedPortal++;
				if(currentSelectedPortal >= portals.size())
					currentSelectedPortal = 0;
			} else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)) {
				if(currentSelectedPortal >= portals.size())
					return;
				Portal destination = portals.get(currentSelectedPortal);
				if(destination.equals(currentPortal)) {
					portalError = "You are already at that portal.";
					doPortalMessage = true;
					return;
				}
				destination.teleportTo(this);
			}
			return;
		}

		if(handler.getKeyManager().isKeyPressed(Controls.WALK)) {
			speed = DEFAULT_SPEED / 2f;
			inaccuracy = currentWeapon.getInaccuracy() / 2f;
		} else if(handler.getKeyManager().isKeyPressed(Controls.CROUCH)) {
			speed = DEFAULT_SPEED / 3.5f;
			inaccuracy = currentWeapon.getInaccuracy() / 3f;
		} else {
			speed = DEFAULT_SPEED;
			inaccuracy = currentWeapon.getInaccuracy();
		}

		if(handler.getMouseManager().isLeftPressed()) {
			attack();
		}

		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_V)) {
			addCoins(20);
		} else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_B)) {
			removeCoins(20);
		}

		if(handler.getKeyManager().isKeyPressed(Controls.SHOOT)) {
			attack();
		}

		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_F)) {
			lb.addXP(20);
		}

		if((handler.getKeyManager().isKeyPressed(KeyEvent.VK_X)
				&& handler.getKeyManager().keyJustPressed(KeyEvent.VK_Z))
				|| (handler.getKeyManager().keyJustPressed(KeyEvent.VK_X)
						&& handler.getKeyManager().isKeyPressed(KeyEvent.VK_Z))) {
			dev = !dev;
		}

		if(handler.getKeyManager().keyJustPressed(Controls.SLOT1)) {
			currentWeaponIndex = 0;
			changeWeapon(weapons[currentWeaponIndex]);
		} else if(handler.getKeyManager().keyJustPressed(Controls.SLOT2)) {
			currentWeaponIndex = 1;
			changeWeapon(weapons[currentWeaponIndex]);
		} else if(handler.getKeyManager().keyJustPressed(Controls.SLOT3)) {
			currentWeaponIndex = 2;
			changeWeapon(weapons[currentWeaponIndex]);
		} else if(handler.getKeyManager().keyJustPressed(Controls.SLOT4)) {
			if(multiplayer)
				currentWeaponIndex = 2;
			else
				currentWeaponIndex = 3;
			changeWeapon(weapons[currentWeaponIndex]);
		} else if(handler.getKeyManager().keyJustPressed(Controls.SLOT5)) {
			currentWeaponIndex = 4;
			changeWeapon(weapons[currentWeaponIndex]);
		}

		if(handler.getKeyManager().keyJustPressed(Controls.RELOAD)) {
			if(reloading)
				return;
			if(currentWeapon.getBullets() != currentWeapon.getClipSize()) {
				firingDelay = firingDelay + reloadTime;
				reloading = true;
			}
		}

		if(handler.getKeyManager().keyJustPressed(Controls.CHAT)) {
			if(handler.getGame().getClient() != null) {
				String msg = JOptionPane.showInputDialog("Enter message");
				if(msg != null) {
					if(!msg.equals("")) {
						kPacket cp = new kPacket("CHAT", 1);
						cp.add(username + " > " + msg);
						handler.getGame().getClient().sendData(gson.toJson(cp).getBytes());
					}
					// handler.getGame().client.sendData(("chat:>/" +
					// getClient() + " > " + msg + "/").getBytes());
				}
			}
		}

		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) {
			if(multiplayer) {
				kPacket dp = new kPacket("DISCONNECT", 1);
				dp.add(username);
				handler.getGame().getClient().sendData(gson.toJson(dp).getBytes());
			}
			// handler.getGame().client.sendData(("discon:>/" + username +
			// "/").getBytes());
			else {
				MenuManager.setMenu(Game.pauseMenu);
			}
		}
	}

	private void getInput() {
		if(died)
			return;
		xMove = 0;
		yMove = 0;

		if(handler.getKeyManager().isKeyPressed(Controls.UP))
			yMove = -speed;
		if(handler.getKeyManager().isKeyPressed(Controls.DOWN))
			yMove = +speed;
		if(handler.getKeyManager().isKeyPressed(Controls.LEFT))
			xMove = -speed;
		if(handler.getKeyManager().isKeyPressed(Controls.RIGHT))
			xMove = speed;

		if(multiplayer) {
			if(handler.getKeyManager().isKeyPressed(Controls.UP) || handler.getKeyManager().isKeyPressed(Controls.DOWN)
					|| handler.getKeyManager().isKeyPressed(Controls.LEFT)
					|| handler.getKeyManager().isKeyPressed(Controls.RIGHT))
				if(x + xMove != y || y + yMove != x) { // TODO: Iffy: x + xMove
														// != y??
					kPacket mp = new kPacket("MOVE", 3);
					mp.add(username);
					mp.add(Float.toString(x));
					mp.add(Float.toString(y));
					handler.getGame().getClient().sendData(gson.toJson(mp).getBytes());
				}
			// handler.getGame().client
			// .sendData(("move:>/" + username + "/" + (int) x + "/" + (int) y +
			// "/").getBytes()); // Improve
			// accuracy
		}
	}

	public void checkPortalCollision() {
		showPortalMenu = false;
		portals = handler.getWorld().getPortalManager().getPortals();
		for(Portal p : portals) {
			Rectangle bb = new Rectangle((int) x, (int) y, width, height);
			Rectangle pb = new Rectangle(p.getX(), p.getY(), p.getWidth(), p.getHeight());
			if(pb.intersects(bb)) {
				showPortalMenu = true;
				currentPortal = p;
			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(animDown.getCurrentFrame(), (int) (x - handler.getCamera().getxOff()),
				(int) (y - handler.getCamera().getyOff()), width, height, null);
		g.setColor(Color.WHITE);
		g.drawLine((int) (x - handler.getCamera().getxOff() + width / 2),
				(int) (y - handler.getCamera().getyOff() + height / 2), handler.getGame().getMouseManager().getMouseX(),
				handler.getGame().getMouseManager().getMouseY());

		Text.drawString(g, username, (int) (x + width / 2 - handler.getCamera().getxOff()),
				(int) (y - 15 - handler.getCamera().getyOff()), true, Color.WHITE, Assets.roboto16);

		if(dev) {
			Text.drawString(g, "Block: " + handler.getWorld().getTile(
					(int) ((handler.getMouseManager().getMouseX() + handler.getCamera().getxOff()) / Tile.TILE_WIDTH),
					(int) ((handler.getMouseManager().getMouseY() + handler.getCamera().getyOff())) / Tile.TILE_WIDTH)
					.getName(), 15, 50, false, Color.WHITE, Assets.roboto16);

			Text.drawString(g, "Mouse Position: " + handler.getMouseManager().getMouseX() + ":"
					+ handler.getMouseManager().getMouseY(), 15, 70, false, Color.WHITE, Assets.roboto16);
			Text.drawString(g, "Player Position: " + x + ":" + y, 15, 90, false, Color.WHITE, Assets.roboto16);
		}
	}

	public void postRender(Graphics g) {
		if(!inventory.isActive()) {
			hb.render(g);
			ab.render(g);
			lb.render(g);

			g.drawImage(coinAnim.getCurrentFrame(), handler.getWidth() - 313, handler.getHeight() - 60, 48, 48, null);
			Text.drawString(g, Integer.toString(coins), handler.getWidth() - 343, handler.getHeight() - 36, true,
					Color.WHITE, Assets.roboto24);

			if(showPortalMenu) {
				g.drawRect((int) (handler.getWidth() - 240), 16, 224, 400);
				g.drawRect((int) (handler.getWidth() - 241), 15, 226, 402);
				Text.drawString(g, "Choose a portal", (int) (handler.getWidth() - 128), 32, true, Color.WHITE,
						Assets.roboto24);
				for(int i = 0; i < handler.getWorld().getPortalManager().getPortals().size(); i++) {
					if(i == currentSelectedPortal)
						portalMenuColor = Color.BLACK;
					else
						portalMenuColor = Menu.DEFAULT_DESELECTION_COLOR;
					Portal p = handler.getWorld().getPortalManager().getPortals().get(i);
					Text.drawString(g, p.getName() + " (" + p.getX() + ":" + p.getY() + ")",
							(int) (handler.getWidth() - 128), 60 + (24 * i), true, portalMenuColor, Assets.roboto24);
				}
			}

			if(doPortalMessage) {
				portalMessageTime++;
				if(portalMessageTime <= maxPortalMessagetime) {
					Text.drawString(g, portalError, handler.getWidth() / 2, handler.getHeight() / 2, true, Color.WHITE,
							Assets.roboto24);
				} else {
					doPortalMessage = false;
					portalMessageTime = 0;
					portalError = defaultPortalError;
				}
			}
		}

		if(multiplayer)
			chat.render(g);
		inventory.render(g);

		if(died) {
			double t = diedTimer / (double) handler.getGame().getFps();
			double time = Utils.limitPrecision(Double.toString(t), 2, false);

			String m = "Time until respawn: " + time;

			Text.drawString(g, m, (handler.getWidth() / 2) - 350, 350, false, Color.WHITE, Assets.roboto64);
		}
	}

	public void changeWeapon(RangedWeapon w) {
		currentWeapon = w;
		firingDelay = currentWeapon.getShootTime();
		reloadTime = currentWeapon.getReloadTime();
	}

	@Override
	public void hurt(int dmg) {
		if(!died) {
			health -= dmg;
			if(health <= 0) {
				die();
			}
		}
	}

	@Override
	public void die() {
		died = true;
		x = respawnX;
		y = respawnY;

		lb.removeXP((int) (lb.getDeathMultiplier() * 20));

		if(multiplayer)
			handler.getGame().getClient().sendData(("die:>/" + username).getBytes());
	}

	public RangedWeapon getCurrentWeapon() {
		return currentWeapon;
	}

	public void setCurrentWeapon(RangedWeapon currentWeapon) {
		this.currentWeapon = currentWeapon;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isMultiplayer() {
		return multiplayer;
	}

	public void setMultiplayer(boolean multiplayer) {
		this.multiplayer = multiplayer;
	}

	public boolean isDev() {
		return dev;
	}

	public void setDev(boolean dev) {
		this.dev = dev;
	}

	public float getRespawnX() {
		return respawnX;
	}

	public void setRespawnX(float x) {
		respawnX = x;
	}

	public float getRespawnY() {
		return respawnY;
	}

	public void setRespawnY(float y) {
		respawnY = y;
	}

	public int getCurrentAmmo() {
		return currentWeapon.getBullets();
	}

	public int getMaxAmmo() {
		return currentWeapon.getClipSize();
	}

	public boolean isReloading() {
		return reloading;
	}

	public Levelbar getLevelBar() {
		return lb;
	}

	public void addCoins(int coins) {
		this.coins += coins;
	}

	public void removeCoins(int coins) {
		this.coins -= coins;
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}
}