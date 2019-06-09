package me.kerdo.shootr.menu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.gfx.Text;
import me.kerdo.shootr.utils.Utils;
import me.kerdo.shootr.utils.handler.Handler;

public class NewMenu extends Menu {
	public String[] options = {"Username: ", "Map: ", "Difficulty: ", "Save name: ", "Start"};
	private String[] values = {"", "", "", "", ""};
	private String[] maps = {"dev", "test"};
	private boolean[] mapEncryptions = {true, false};
	private int selectedOption = 0, selectedMap = 0;
	public static int MAX_DIFFICULTY = 3, DEFAULT_DIFFICULTY = 2;
	private int difficulty;
	private Color color;
	private boolean created = false;
	private StringBuilder sb;
	
	public NewMenu(Handler handler) {
		super(handler);
		values[2] = Integer.toString(DEFAULT_DIFFICULTY);
		difficulty = Integer.parseInt(values[2]);
		values[1] = maps[selectedMap];
		sb = new StringBuilder();
	}

	@Override
	public void tick() {
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN)) {
			if(selectedOption == options.length - 1)
				selectedOption = 0;
			else
				selectedOption++;
		} else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP)) {
			if(selectedOption <= 0)
				selectedOption = options.length - 1;
			else
				selectedOption--;
		} else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ENTER)) {
			if(options[selectedOption].startsWith("Username")) {
				String username = JOptionPane.showInputDialog("Enter username");
				if(username != null && !username.equals("")) {
					values[0] = username;
				}
			} else if(options[selectedOption].equalsIgnoreCase("start")) {
				if(!created) {
					if(values[3].equals("") || values[0].equals("") || values[1].equals("")) {
						JOptionPane.showMessageDialog(new JFrame(), "Invalid parameters");
						return;
					}
					newSave(values[3], values[0], values[1], mapEncryptions[selectedMap], difficulty);
					created = true;
				}
			} else if(options[selectedOption].startsWith("Save")) {
				String saveName = JOptionPane.showInputDialog("Enter username");
				if(saveName != null && !saveName.equals("")) {
					values[3] = saveName;
				}
			}
		} else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) {
			MenuManager.setMenu(MenuManager.getPreviousMenu());
		}
		
		if(options[selectedOption].startsWith("Difficulty")) {
			if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_LEFT)) {
				difficulty--;
				if(difficulty < 1)
					difficulty = MAX_DIFFICULTY;
				values[selectedOption] = Integer.toString(difficulty);
			} else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_RIGHT)) {
				difficulty++;
				if(difficulty > MAX_DIFFICULTY)
					difficulty = 1;
				values[selectedOption] = Integer.toString(difficulty);
			}
		} else if(options[selectedOption].startsWith("Map")) {
			if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_LEFT)) {
				selectedMap--;
				if(selectedMap < 0)
					selectedMap = maps.length - 1;
				values[1] = maps[selectedMap];
			} else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_RIGHT)) {
				selectedMap++;
				if(selectedMap >= maps.length)
					selectedMap = 0;
				values[1] = maps[selectedMap];
			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.mainMenuBG, 0, 0, null);
		Text.drawString(g, "Choose options for new save:", Menu.DEFAULT_MENU_X, Menu.DEFAULT_MENU_Y, false, Color.WHITE, Assets.roboto24);
		for(int i = 0; i < options.length; i++) {
			if(i == selectedOption)
				color = DEFAULT_SELECTION_COLOR;
			else
				color = DEFAULT_DESELECTION_COLOR;
			if(i == values.length - 1) {
				Text.drawString(g, options[i], Menu.DEFAULT_MENU_X, Menu.DEFAULT_MENU_Y + 75 + (DEFAULT_GAP * i), false, color, Assets.roboto24);
			} else {
				Text.drawString(g, options[i] + values[i], Menu.DEFAULT_MENU_X, Menu.DEFAULT_MENU_Y + 50 + (DEFAULT_GAP * i), false, color, Assets.roboto24);
			}
		}
		
		Text.drawString(g, BACK_TEXT, 5, handler.getHeight() - 5, false, Color.WHITE, Assets.roboto16);
	}
	
	public void usernameAdd(String s) {
		sb.append(s);
		values[0] = sb.toString();
	}
	
	public void newSave(String save, String username, String map, boolean encrypted, int difficulty) {
		try{
		    PrintWriter writer = new PrintWriter(Utils.DIR + "saves/" + save + ".json", "UTF-8");
		    String content = "{\"entities\": {\"trees\": [{\"x\": 100,\"y\": 100, \"health\": 250},{\"x\": 200,\"y\": 100, \"health\": 100}],\"posts\":[],\"enemies\":[{\"x\":500.0,\"y\":600.0,\"health\":500},{\"x\":500.0,\"y\":800.0,\"health\":500}]},\"portals\":[{\"x\":100,\"y\":100,\"name\":\"Portal1\"},{\"x\":1000,\"y\":800,\"name\":\"Portal2\"}],\"player\": {\"x\": 500,\"y\": 400,\"health\": 100,\"username\": \"" + username + "\",\"xp\":0},\"map\": \"" + map + "\",\"map_encryption\": " + encrypted + ",\"difficulty\":" + difficulty + "}";
		    writer.write(content);
		    writer.close();
		    String data = Utils.loadFileAsStringFromDisk("saves/saves.txt");
		    data = data + save + ",";
		    writer = new PrintWriter(Utils.DIR + "saves/saves.txt");
		    writer.write(data);
		    writer.close();
		    MenuManager.setMenu(new GameMenu(handler, save));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(new JFrame(), "An unexpected error occurred");
			e.printStackTrace();
		}
	}
}