package lx.lindx.drive.server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lx.lindx.drive.server.util.Config;
import lx.lindx.drive.server.util.Util;

/**
 * Server
 */
public class Server extends Thread {

  private Socket socket;
  private final int PORT;

  public Server() {
    this(Config.getPort());
  }

  public Server(final int port) {
    this.PORT = port;
  }

  @Override
  public void run() {

    Util.log(Util.getStr("srv.start") + " -> " + PORT);
    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
      while (true) {
        Util.log("srv.wait");
        socket = serverSocket.accept();
        Util.log("Client" + Util.getAddress(socket) + "connected!");
        new Connection(socket, this).start();
      }
    } catch (IOException e) {

    }

  }
}