package me.kerdo.shootr.gfx;

import me.kerdo.shootr.input.KeyManager;
import me.kerdo.shootr.input.MouseManager;

import javax.swing.*;
import java.awt.*;

public class Display {
  private JFrame frame;
  private Canvas canvas;

  private final String title;
  private final int width, height;

  public Display(final String title, final int width, final int height) {
    this.title = title;
    this.width = width;
    this.height = height;

    init();
  }

  private void init() {
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

  public void setListeners(final KeyManager keyManager, final MouseManager mouseManager) {
    frame.addKeyListener(keyManager);
    frame.addMouseListener(mouseManager);
    frame.addMouseMotionListener(mouseManager);
    canvas.addMouseListener(mouseManager);
    canvas.addMouseMotionListener(mouseManager);
    frame.addMouseWheelListener(mouseManager);
    canvas.addMouseWheelListener(mouseManager);
  }

  public Canvas getCanvas() {
    return canvas;
  }

  public JFrame getFrame() {
    return frame;
  }
}
