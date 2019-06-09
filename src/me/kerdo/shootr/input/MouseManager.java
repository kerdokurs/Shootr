package me.kerdo.shootr.input;

import me.kerdo.shootr.gfx.ui.UIManager;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseManager implements MouseListener, MouseMotionListener, MouseWheelListener {
  private UIManager uiManager;

  private boolean leftPressed, rightPressed, midPressed;
  private int mouseX, mouseY;
  private int mouseWheelPos = 0;

  public MouseManager() {
  }

  @Override
  public void mousePressed(final MouseEvent e) {
    if (e.getButton() == MouseEvent.BUTTON1)
      leftPressed = true;
    else if (e.getButton() == MouseEvent.BUTTON3)
      rightPressed = true;
    else if (e.getButton() == MouseEvent.BUTTON2)
      midPressed = true;

    if (uiManager != null)
      uiManager.mousePressed(e);
  }

  @Override
  public void mouseReleased(final MouseEvent e) {
    if (e.getButton() == MouseEvent.BUTTON1)
      leftPressed = false;
    else if (e.getButton() == MouseEvent.BUTTON3)
      rightPressed = false;
    else if (e.getButton() == MouseEvent.BUTTON2)
      midPressed = false;

    if (uiManager != null)
      uiManager.mouseReleased(e);
  }

  @Override
  public void mouseMoved(final MouseEvent e) {
    mouseX = e.getX();
    mouseY = e.getY();

    if (uiManager != null)
      uiManager.mouseMoved(e);
  }

  @Override
  public void mouseWheelMoved(final MouseWheelEvent e) {
    if (uiManager != null)
      uiManager.mouseWheelMoved(e);
  }

  @Override
  public void mouseDragged(final MouseEvent e) {
    mouseX = e.getX();
    mouseY = e.getY();

    if (uiManager != null)
      uiManager.mouseDragged(e);
  }

  @Override
  public void mouseClicked(final MouseEvent e) {
    if (e.getButton() == MouseEvent.BUTTON1)
      leftPressed = false;
    else if (e.getButton() == MouseEvent.BUTTON3)
      rightPressed = false;
    else if (e.getButton() == MouseEvent.BUTTON2)
      midPressed = false;

    if (uiManager != null)
      uiManager.mouseClicked(e);
  }

  @Override
  public void mouseEntered(final MouseEvent e) {
    if (uiManager != null)
      uiManager.mouseEntered(e);
  }

  @Override
  public void mouseExited(final MouseEvent e) {
    if (uiManager != null)
      uiManager.mouseExited(e);
  }

  public boolean isLeftPressed() {
    return leftPressed;
  }

  public boolean isRightPressed() {
    return rightPressed;
  }

  public boolean isMidPressed() {
    return midPressed;
  }

  public int getMouseX() {
    return mouseX;
  }

  public int getMouseY() {
    return mouseY;
  }

  public int getMouseWheelPos() {
    return mouseWheelPos;
  }

  public UIManager getUiManager() {
    return uiManager;
  }

  public void setUiManager(final UIManager uiManager) {
    this.uiManager = uiManager;
  }
}
