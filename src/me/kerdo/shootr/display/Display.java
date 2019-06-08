package me.kerdo.shootr.display;

import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.JFrame;

import me.kerdo.shootr.input.KeyManager;
import me.kerdo.shootr.input.MouseManager;

public class Display {
	private JFrame frame;
	private Canvas canvas;
	
	private String title;
	private int width, height;
	
	public Display(String title, int width, int height) {
		this.title = title;
		this.width= width;
		this.height = height;
		
		createDisplay();
	}
	
	private void createDisplay() {
		frame = new JFrame(title);
		frame.setSize(width, height);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		canvas.setFocusable(false);
		
		frame.add(canvas);
		frame.pack();
	}
	
	public void setListeners(KeyManager keyManager, MouseManager mouseManager) {
		frame.addKeyListener(keyManager);
		frame.addMouseListener(mouseManager);
		frame.addMouseMotionListener(mouseManager);
		canvas.addMouseListener(mouseManager);
		canvas.addMouseMotionListener(mouseManager);
		frame.addMouseWheelListener(mouseManager);
		canvas.addMouseWheelListener(mouseManager);
	}
	
	public void setCursor(Cursor c) {
		frame.setCursor(c);
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public JFrame getFrame() {
		return frame;
	}
}