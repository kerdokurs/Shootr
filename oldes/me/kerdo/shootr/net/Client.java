package me.kerdo.shootr.net;

import java.awt.Color;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Iterator;

import javax.swing.JOptionPane;

import com.google.gson.Gson;

import me.kerdo.shootr.net.kPacket;
import me.kerdo.shootr.audio.Audio;
import me.kerdo.shootr.entity.creature.PlayerMP;
import me.kerdo.shootr.menu.GameMenu;
import me.kerdo.shootr.menu.MenuManager;
import me.kerdo.shootr.utils.handler.Handler;
import me.kerdo.shootr.weapons.Bullet;

public class Client extends Thread {
	private Handler handler;
	private DatagramSocket socket;
	private InetAddress ipAddress;
	private int port = 1888;
	private int dX = 350, dY = 350;

	public Client(Handler handler, String ipAddress) {
		this.handler = handler;
		try {
			socket = new DatagramSocket();
			this.ipAddress = InetAddress.getByName(ipAddress);
		} catch(SocketException e) {
			e.printStackTrace();
		} catch(UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public void sendData(byte[] data) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
		try {
			socket.send(packet);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while(true) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch(IOException e) {
				e.printStackTrace();
			}

			String d = new String(packet.getData()).trim();
			System.out.println("SERVER > " + d);
			
			Gson gson = new Gson();
			kPacket p = gson.fromJson(d, kPacket.class);

			if(p.getType().equals("/UP")) {
				String username = JOptionPane.showInputDialog("Enter your username");
				if(username != null && !username.equals("")) {
					handler.getWorld().getEntityManager().getPlayer().setMultiplayer(true);
					kPacket jp = new kPacket("CONNECT", 3);
					jp.add(username);
					jp.add(Integer.toString(dX));
					jp.add(Integer.toString(dY));
					sendData(gson.toJson(jp).getBytes());/*("con:>/" + username + "/" + dX + "/" + dY + "/")*/
					handler.getWorld().getEntityManager().getPlayer().setUsername(username);
					handler.getWorld().getEntityManager().getPlayer().setX(dX);
					handler.getWorld().getEntityManager().getPlayer().setY(dY);
					MenuManager.setMenu(new GameMenu(handler, "save01"));
				}
			}
			
			if(p.getType().equals("/SHUTDOWN")) {
				String shutdownCause = p.getMetadata()[0];
				handler.getWorld().getEntityManager().getPlayer().chat.append(shutdownCause);
				try {
					Thread.sleep(1000);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
				System.exit(1);
//				handler.getWorld().getEntityManager().getMpPlayers().clear();
//				handler.getWorld().getEntityManager().getEntities().clear();
//				handler.getGame().client = null;
//				GameStateManager.setState(handler.getGame().menuState);
			}

			if(p.getType().equals("/NEW")) {
				String username = p.getMetadata()[0];
				float x = Float.parseFloat(p.getMetadata()[1]);
				float y = Float.parseFloat(p.getMetadata()[2]);
				PlayerMP pmp = new PlayerMP(handler, x, y, username);
				handler.getWorld().getEntityManager().addEntity(pmp);
//				handler.getWorld().getEntityManager().getMpPlayers().add(pmp); //addEntity(e); does it for us. Might not work.
				handler.getWorld().getEntityManager().getPlayer().chat.append(username + " has joined.");
//				if(dd[1] != null && dd[2] != null && dd[3] != null) {
//					String username = dd[1];
//					int x = Integer.parseInt(dd[2]), y = Integer.parseInt(dd[3]);
//					PlayerMP pmp = new PlayerMP(handler, x, y, username);
//					handler.getWorld().getEntityManager().addEntity(pmp);
//					handler.getWorld().getEntityManager().getMpPlayers().add(pmp);
//					handler.getWorld().getEntityManager().getPlayer().chat.append(username + " has joined.");
//				}
			}

			if(p.getType().equals("/MOVE")) {
				String username = p.getMetadata()[0];
				float x = Float.parseFloat(p.getMetadata()[1]);
				float y = Float.parseFloat(p.getMetadata()[2]);
				if(handler.getWorld().getEntityManager().getPlayer().getUsername().equals(username)) {
					handler.getWorld().getEntityManager().getPlayer().setX(x);
					handler.getWorld().getEntityManager().getPlayer().setY(y);
				} else {
					for(PlayerMP pm : handler.getWorld().getEntityManager().getMpPlayers()) {
						if(pm.getUsername().equals(username)) {
							pm.setX(x);
							pm.setY(y);
						}
					}
				}
				
//				if(dd[1] != null && dd[2] != null && dd[3] != null) {
//					String username = dd[1];
//					int x = Integer.parseInt(dd[2]), y = Integer.parseInt(dd[3]);
//					if(handler.getWorld().getEntityManager().getPlayer().getUsername().equals(username)) {
//						handler.getWorld().getEntityManager().getPlayer().setX(x);
//						handler.getWorld().getEntityManager().getPlayer().setY(y);
//					} else {
//						for(PlayerMP pm : handler.getWorld().getEntityManager().getMpPlayers()) {
//							if(pm.getUsername().equals(username)) {
//								pm.setX(x);
//								pm.setY(y);
//							}
//						}
//					}
//				}
			}
			
			if(p.getType().equals("/SHOOT")) {
				String username = p.getMetadata()[0];
				float x = Float.parseFloat(p.getMetadata()[1]);
				float y = Float.parseFloat(p.getMetadata()[2]);
				int size = Integer.parseInt(p.getMetadata()[3]);
				double speed = Double.parseDouble(p.getMetadata()[4]);
				float angle = Float.parseFloat(p.getMetadata()[5]);
				float damage = Float.parseFloat(p.getMetadata()[6]);
				float maxLifeTime = Float.parseFloat(p.getMetadata()[7]);
				handler.getWorld().getBulletManager().addBullet(new Bullet(handler, angle, x, y, speed, size, Color.BLUE, damage, username, maxLifeTime));
				Audio.playSound("gunshot.wav", -17.5f);
				
//				System.out.println(dd[1]);
//				String username = dd[1];
//				float x = Float.parseFloat(dd[2]), y = Float.parseFloat(dd[3]);
//				int size = Integer.parseInt(dd[4]);
//				double speed = Double.parseDouble(dd[5]);
//				float angle = Float.parseFloat(dd[6]), damage = Float.parseFloat(dd[7]);
//				handler.getWorld().getBulletManager()
//						.addBullet(new Bullet(handler, angle, x, y, speed, size, Color.BLUE, damage, username)); //TODO:Fix up
//				Audio.playSound("gunshot.wav", -17.5f);
			}
			
			if(p.getType().equals("/CHAT")) {
				String message = p.getMetadata()[0];
				handler.getWorld().getEntityManager().getPlayer().chat.append(message);
				
//				System.out.println(dd[1]);
//				handler.getWorld().getEntityManager().getPlayer().chat.append(dd[1]);
			}
			
			if(p.getType().equals("/KICK")) {
				String username = p.getMetadata()[0];
				String kickCause = p.getMetadata()[1];
				if(handler.getWorld().getEntityManager().getPlayer().getUsername().equals(username)) {
					handler.getWorld().getEntityManager().getPlayer().chat.append("You have been kicked: " + kickCause);
				} else {
					Iterator<PlayerMP> it = handler.getWorld().getEntityManager().getMpPlayers().iterator();
					while(it.hasNext()) {
						PlayerMP pmp = it.next();
						if(pmp.getUsername().equals(username)) {
							it.remove();
							handler.getWorld().getEntityManager().getMpPlayers().remove(pmp);
							handler.getWorld().getEntityManager().getEntities().remove(pmp);
							handler.getWorld().getEntityManager().getPlayer().chat.append(username + " was kicked: " + kickCause);
						}
					}
				}
				
//				String username = dd[1];
//				if(handler.getWorld().getEntityManager().getPlayer().getUsername().equals(username)) {
//					System.exit(1);
//				} else {
//					Iterator<PlayerMP> it = handler.getWorld().getEntityManager().getMpPlayers().iterator();
//					while(it.hasNext()) {
//						PlayerMP pmp = it.next();
//						if(pmp.getUsername().equals(username)) {
//							it.remove();
//							handler.getWorld().getEntityManager().getMpPlayers().remove(pmp);
//							handler.getWorld().getEntityManager().getEntities().remove(pmp);
//							handler.getWorld().getEntityManager().getPlayer().chat.append(username + " got kicked.");
//						}
//					}
//				}
			}

			if(p.getType().equals("/DISCONNECT")) {
				if(p.getMetadata()[0].equals("true")) {
					System.exit(1);
				}
				
				String username = p.getMetadata()[0];
				Iterator<PlayerMP> it = handler.getWorld().getEntityManager().getMpPlayers().iterator();
				while(it.hasNext()) {
					PlayerMP pmp = it.next();
					if(pmp.getUsername().equals(username)) {
						it.remove();
						handler.getWorld().getEntityManager().getMpPlayers().remove(pmp);
						handler.getWorld().getEntityManager().getEntities().remove(pmp);
						handler.getWorld().getEntityManager().getPlayer().chat.append(username + " has disconnected");
					}
				}
				
//				if(dd[1].equals("true")) {
//					GameStateManager.setState(handler.getGame().menuState);
//				}
//				String username = dd[1];
				
//				for(int i = 0; i < handler.getWorld().getEntityManager().getMpPlayers().size(); i++) {
//					PlayerMP mp = handler.getWorld().getEntityManager().getMpPlayers().get(i);
//					if(mp.getUsername().equals(username)) {
//						System.out.println(username + " has left.");
//						handler.getWorld().getEntityManager().getMpPlayers().remove(mp);
//						return;
//					}
//				}

//				for(Iterator<PlayerMP> it = handler.getWorld().getEntityManager().getMpPlayers().iterator(); it
//						.hasNext();) {
//					
//				}

//				Iterator<PlayerMP> it = handler.getWorld().getEntityManager().getMpPlayers().iterator();
//				while(it.hasNext()) {
//					PlayerMP pmp = it.next();
//					if(pmp.getUsername().equals(username)) {
//						it.remove();
//						handler.getWorld().getEntityManager().getMpPlayers().remove(pmp);
//						handler.getWorld().getEntityManager().getEntities().remove(pmp);
//						handler.getWorld().getEntityManager().getPlayer().chat.append(username + " has disconnected");
//					}
//				}
			}
		}
	}
}