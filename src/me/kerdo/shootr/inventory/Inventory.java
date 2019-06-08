package me.kerdo.shootr.inventory;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.gfx.Text;
import me.kerdo.shootr.items.Item;
import me.kerdo.shootr.utils.InvPos;
import me.kerdo.shootr.utils.handler.Handler;

public class Inventory {
	private Handler handler;
	private boolean active = false;
	
	private static final int itemIconX = 966, itemIconY = 83,
							 itemCountX = 1025, itemCountY = 251;
	public static final int DEFAULT_ITEM_INDEX = 0;
	
	private int currentItemIndex = DEFAULT_ITEM_INDEX;
	
	private ArrayList<Item> inventoryItems;
	
	public Inventory(Handler handler) {
		this.handler = handler;
		
		inventoryItems = new ArrayList<Item>();
		
		InvPos.init();
	}
	
	public void tick() {
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_E)) {
			active = !active;
			currentItemIndex = DEFAULT_ITEM_INDEX;
		}
		
		if(!active)
			return;
		
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_RIGHT)) {
			currentItemIndex += 1;
			if(currentItemIndex == 32)
				currentItemIndex = 0;
		} else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_LEFT)) {
			currentItemIndex -= 1;
			if(currentItemIndex == -1)
				currentItemIndex = 31;
		} else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP)) {
			currentItemIndex -= 8;
			if(currentItemIndex < 0)
				currentItemIndex = 31 - -currentItemIndex;
		} else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN)) {
			currentItemIndex += 8;
			if(currentItemIndex > 31)
				currentItemIndex = currentItemIndex - 31;
		} 
	}
	
	public void render(Graphics g) {
		if(!active)
			return;
		
		g.drawImage(Assets.inventory, 40, 30, null);
		
		for(int i = 0; i < inventoryItems.size(); i++) {
			Item it = inventoryItems.get(i);
			g.drawImage(it.getTexture(), InvPos.xPos[i], InvPos.yPos[i], 64, 64, null);
		}
		
		g.setColor(Color.WHITE);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		g2.drawRect(InvPos.xPos[currentItemIndex], InvPos.yPos[currentItemIndex], 64, 64);
		
		Text.drawString(g, ">" + currentItemIndex, 20, 50, false, Color.WHITE, Assets.roboto16);
		
		try {
			g.drawImage(inventoryItems.get(currentItemIndex).getTexture(), itemIconX, itemIconY, 120, 120, null);
			Text.drawString(g, Integer.toString(inventoryItems.get(currentItemIndex).getCount()), itemCountX, itemCountY, true, Color.WHITE, Assets.roboto24);
		} catch(IndexOutOfBoundsException e) {
		}
	}
	
	public void addItem(Item item, int count) {
		for(int c = 0; c < count; c++) {
			for(Item i : inventoryItems) {
				if(i.getId() == item.getId()) {
					i.setCount(i.getCount() + item.getCount());
					return;
				}
			}
			inventoryItems.add(item);
		}
	}
	
	public void removeItem(Item item) {
		
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}