package me.kerdo.shootr.items;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import me.kerdo.shootr.utils.handler.Handler;

public class ItemManager {
	private Handler handler;
	private ArrayList<Item> items;
	public ItemManager(Handler handler) {
		this.handler = handler;
		
		items = new ArrayList<Item>();	
	}
	
	public void tick() {
		Iterator<Item> it = items.iterator();
		while(it.hasNext()) {
			Item i = it.next();
			i.tick();
			if(i.isPickedUp())
				it.remove();
		}
	}
	
	public void render(Graphics g) {
		for(Item i : items) {
			if(i.getName().equals("Gold")) {
				i.render(g, i.getX(), i.getY(), 16, 16);
			} else {
				i.render(g);
			}
		}
	}
	
	public void addItem(Item i) {
		i.setHandler(handler);
		items.add(i);
	}

	public Handler getHandler() {
		return handler;
	}
	
	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public ArrayList<Item> getItems() {
		return items;
	}
}