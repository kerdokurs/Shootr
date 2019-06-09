package me.kerdo.shootr.chat;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import me.kerdo.shootr.gfx.Assets;
import me.kerdo.shootr.gfx.Text;
import me.kerdo.shootr.utils.handler.Handler;

public class Chat {
	private Handler handler;
	private int x, y;
	private ArrayList<String> chats;
	public Chat(Handler handler, int x, int y) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		chats = new ArrayList<String>();
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		int line = 1;
		g.drawImage(Assets.chat, x, y, (int) (280 * 1.5), (int) (110 * 1.5), null);
		
		Iterator<String> it = chats.iterator();
		while(it.hasNext()) {
			String s = it.next();
			if(line >= 9) {
				chats.remove(0);
				return;
			}
			
//			StringBuilder sb = new StringBuilder();
//			for(int i = 0; i < s.length(); i++) {
//				sb.append(s.charAt(i));
//				if(i > 0 && (i % 50 == 0)) {
//					Text.drawString(g, sb.toString(), x + 10, y + 19 * line, false, Color.WHITE, Assets.font16);
//					sb.delete(0, sb.length() - 1);
//					line++;
//				}
//				Text.drawString(g, sb.toString(), x + 10, y + 19 * line, false, Color.WHITE, Assets.font16);
//			}
//			TODO: Make word-wrap
			
			Text.drawString(g, s, x + 10, y + 19 * line, false, Color.WHITE, Assets.roboto16);
			line++;
		}
	}
	
	public void append(String data) {
		chats.add(new String(data));
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}
}