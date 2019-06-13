package me.kerdo.shootr.net;

import me.kerdo.shootr.Game;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {
  private final Game game;
  private final InetAddress ip;
  private final int port;

  private boolean shouldClose = false;

  private Socket socket;
  private Scanner in;
  private PrintWriter out;

  public Client(final Game game, final InetAddress ip, final int port) {
    this.game = game;
    this.ip = ip;
    this.port = port;
  }

  @Override
  public void run() {
    try {
      socket = new Socket(ip, port);
      in = new Scanner(socket.getInputStream());
      out = new PrintWriter(socket.getOutputStream());

      while (!shouldClose && in.hasNextLine()) {
        final String line = in.nextLine();

        System.out.println("line = " + line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
