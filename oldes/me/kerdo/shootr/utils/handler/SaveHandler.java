package me.kerdo.shootr.utils.handler;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.kerdo.shootr.entity.Entity;
import me.kerdo.shootr.entity.creature.Enemy;
import me.kerdo.shootr.entity.statik.Post;
import me.kerdo.shootr.entity.statik.StaticEntity;
import me.kerdo.shootr.entity.statik.Tree;
import me.kerdo.shootr.portal.Portal;
import me.kerdo.shootr.utils.Utils;

public class SaveHandler {
	private Handler handler;
	private String saveFile;
	private float[] playerData;
	private String mapFile;
	private boolean mapEncryption;
	private String playerUsername;
	private int difficulty;

	public ArrayList<StaticEntity> allEntities;
	public ArrayList<Enemy> allEnemies;
	public ArrayList<Portal> allPortals;

	public SaveHandler(Handler handler, String saveFile) {
		this.handler = handler;
		this.saveFile = saveFile;
		allEntities = new ArrayList<StaticEntity>();
		allEnemies = new ArrayList<Enemy>();
		allPortals = new ArrayList<Portal>();
		loadSave(saveFile);
	}

	public void loadSave(String saveFile) {
		String data = Utils.loadFileAsStringFromDisk("saves/" + saveFile + ".json");
		JsonParser jp = new JsonParser();
		JsonElement base = jp.parse(data);

		JsonObject main = base.getAsJsonObject();
		JsonObject entities = main.getAsJsonObject("entities");

		JsonArray trees = entities.getAsJsonArray("trees");
		JsonArray posts = entities.getAsJsonArray("posts");
		JsonArray enemies = entities.getAsJsonArray("enemies");
		JsonArray portals = main.get("portals").getAsJsonArray();

		for(int i = 0; i < trees.size(); i++) {
			JsonElement temp = trees.get(i);
			JsonObject choords = temp.getAsJsonObject();
			int x = choords.get("x").getAsInt();
			int y = choords.get("y").getAsInt();
			int h = choords.get("health").getAsInt();
			Tree tr = new Tree(handler, x, y);
			tr.setHealth(h);
			allEntities.add(tr);
		}

		for(int i = 0; i < posts.size(); i++) {
			JsonElement temp = posts.get(i);
			JsonObject choords = temp.getAsJsonObject();
			int x = choords.get("x").getAsInt();
			int y = choords.get("y").getAsInt();
			int h = choords.get("health").getAsInt();
			Post po = new Post(handler, x, y);
			po.setHealth(h);
			allEntities.add(po);
		}
		
		for(int i = 0; i < enemies.size(); i++) {
			JsonElement temp = enemies.get(i);
			JsonObject choords = temp.getAsJsonObject();
			int x = choords.get("x").getAsInt();
			int y = choords.get("y").getAsInt();
			int h = choords.get("health").getAsInt();
			Enemy en = new Enemy(handler, x, y);
			en.setHealth(h);
			allEnemies.add(en);
		}
		
		for(int i = 0; i < portals.size(); i++) {
			JsonElement temp = portals.get(i);
			JsonObject choords = temp.getAsJsonObject();
			int x = choords.get("x").getAsInt();
			int y = choords.get("y").getAsInt();
			String name = choords.get("name").getAsString();
			Portal portal = new Portal(handler, name, x, y);
			allPortals.add(portal);
		}

		JsonObject pl = main.getAsJsonObject("player");
		float x = pl.get("x").getAsFloat();
		float y = pl.get("y").getAsFloat();
		float h = pl.get("health").getAsFloat();
		int xp = pl.get("xp").getAsInt();
		playerData = new float[4];
		playerData[0] = x;
		playerData[1] = y;
		playerData[2] = h;
		playerData[3] = xp;

		mapFile = main.get("map").getAsString();
		mapEncryption = main.get("map_encryption").getAsBoolean();
		playerUsername = pl.get("username").getAsString();
		difficulty = main.get("difficulty").getAsInt();
	}

	public String saveSave(String save, ArrayList<Entity> entities, ArrayList<Portal> portals, float playerX, float playerY, int playerHealth,
			String playerUsername, int xp, String map, boolean encrypted, int difficulty) {
		try {
			PrintWriter writer = new PrintWriter(Utils.DIR + "saves/" + save + ".json", "UTF-8");

			StringBuilder sb = new StringBuilder();
			StringBuilder tb = new StringBuilder();
			StringBuilder pb = new StringBuilder();
			StringBuilder eb = new StringBuilder();
			StringBuilder pob = new StringBuilder();
			sb.append("{\"entities\":{\"trees\":[");
			pb.append("\"posts\":[");
			eb.append("\"enemies\":[");
			for(Entity e : entities) {
				if(e instanceof Tree) {
					tb.append("{\"x\":" + e.getX() + ",\"y\":" + e.getY() + ",\"health\":" + e.getHealth() + "},");
				} else if(e instanceof Post) {
					pb.append("{\"x\":" + e.getX() + ",\"y\":" + e.getY() + ",\"health\":" + e.getHealth() + "},");
				} else if(e instanceof Enemy) {
					eb.append("{\"x\":" + e.getX() + ",\"y\":" + e.getY() + ",\"health\":" + e.getHealth() + "},");
				}
			}
			
			pob.append("\"portals\":[");
			for(Portal p : portals) {
				pob.append("{\"x\":" + p.getX() + ",\"y\":" + p.getY() + ",\"name\":\"" + p.getName() + "\"},");
			}
			
			String ts = new String(tb.toString());
			if(ts.endsWith(",")) {
				ts = ts.substring(0, ts.length() - 1);
			}
			sb.append(ts + "],");

			String ps = new String(pb.toString());
			if(ps.endsWith(",")) {
				ps = ps.substring(0, ps.length() - 1);
			}
			sb.append(ps + "],");
			
			String es = new String(eb.toString());
			if(es.endsWith(",")) {
				es = es.substring(0, es.length() - 1);
			}
			sb.append(es + "]},");
			
			String pos = new String(pob.toString());
			if(pos.endsWith(",")) {
				pos = pos.substring(0, pos.length() - 1);
			}
			sb.append(pos + "],");

			String pls = "\"player\":{\"x\":" + playerX + ",\"y\":" + playerY + ",\"health\":" + playerHealth
					+ ",\"username\":\"" + playerUsername + "\",\"xp\":" + xp + "},";
			sb.append(pls);

			String ms = "\"map\":\"" + map + "\",\"map_encryption\":" + encrypted + ",\"difficulty\":" + difficulty;
			sb.append(ms);
			sb.append("}");

			String content = sb.toString();
			writer.write(content);
			writer.close();
			return save + " saved successfully to disk.";
		} catch(FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
			return "Something went wrong while saving.";
		}
	}

	public float[] getPlayerData() {
		return playerData;
	}

	public String getSaveFile() {
		return saveFile;
	}

	public String getMapFile() {
		return mapFile;
	}

	public boolean getMapEncryption() {
		return mapEncryption;
	}

	public String getPlayerUsername() {
		return playerUsername;
	}

	public int getDifficulty() {
		return difficulty;
	}
}