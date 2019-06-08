package me.kerdo.shootr.menu;

public class MenuManager {
	private static Menu currentMenu = null;
	private static Menu previousMenu = null;
	private static boolean first = true;

	public static void setMenu(Menu menu) {
		if(first) {
			previousMenu = menu;
			currentMenu = menu;
			first = false;
		} else {
			previousMenu = currentMenu;
			currentMenu = menu;
		}
	}

	public static Menu getMenu() {
		return currentMenu;
	}

	public static Menu getPreviousMenu() {
		return previousMenu;
	}

	public static void setPreviousMenu(Menu previousMenu) {
		MenuManager.previousMenu = previousMenu;
	}
}