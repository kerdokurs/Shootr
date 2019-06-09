package me.kerdo.shootr.items;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.utils.handler.Handler;

public class Item {

	public static Item[] items = new Item[256];
	
	public static Item wood = new Item(Assets.wood, "Wood", 0);
	
	public static final int ITEM_WIDTH = 64,
							ITEM_HEIGHT = 64;
	protected Handler handler;
	protected BufferedImage texture;
	protected String name;
	protected final int id;
	protected Rectangle bounds;
	
	protected int x, y, count;
	protected boolean pickedUp = false;
	
	public Item(BufferedImage texture, String name, int id) {
		this.texture = texture;
		this.name = name;
		this.id = id;
		this.count = 1;
		
		items[id] = this;
		
		bounds = new Rectangle(x, y, ITEM_WIDTH, ITEM_HEIGHT);
	}
	
	public void tick() {
		if(handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0, 0).intersects(bounds)) {
			pickedUp = true;
			int rand = (int) (Math.round(Math.random() * 2) + 1);
			handler.getWorld().getEntityManager().getPlayer().getInventory().addItem(this, rand);
		}
	}

	public void render(Graphics g) {
		if(handler == null)
			return;
		render(g, (int) (x - handler.getCamera().getxOff()), (int) (y - handler.getCamera().getyOff()));
	}
	
	public void render(Graphics g, int x, int y) {
		g.drawImage(texture, x, y, ITEM_WIDTH, ITEM_HEIGHT, null);
	}
	
	public void render(Graphics g, int x, int y, int width, int height) {
		g.drawImage(texture, (int) (x - handler.getCamera().getxOff()), (int) (y - handler.getCamera().getyOff()), width, height, null);
	}
	
	public Item createNew(int count) { //TODO: Remove later!
		Item i = new Item(texture, name, id);
		i.setPickedUp(true);
		i.setCount(count);
		return i;
	}
	
	public Item createNew(int x, int y) {
		Item i = new Item(texture, name, id);
		i.setPosition(x, y);
		return i;
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		bounds.x = x;
		bounds.y = y;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public BufferedImage getTexture() {
		return texture;
	}

	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setPickedUp(boolean pickedUp) {
		this.pickedUp = pickedUp;
	}
	
	public boolean isPickedUp() {
		return pickedUp;
	}
}