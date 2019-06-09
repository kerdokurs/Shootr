package me.kerdo.shootr.portal;

import java.awt.Graphics;
import java.util.ArrayList;

public class PortalManager {	
	private ArrayList<Portal> portals;
	
	public PortalManager() {
		portals = new ArrayList<Portal>();
	}
	
	public void tick() {
		for(Portal p : portals) {
			p.tick();
		}
	}
	
	public void render(Graphics g) {
		for(Portal p : portals) {
			p.render(g);
		}
	}
	
	public void addPortal(Portal portal) {
		portals.add(portal);
	}
	
	public void removePortal(Portal portal) {
		portals.remove(portal);
	}
	
	public ArrayList<Portal> getPortals() {
		return portals;
	}
}