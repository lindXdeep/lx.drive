package lx.lindx.drive.server.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import lombok.Data;
import lx.lindx.drive.server.net.Connection;
import lx.lindx.drive.server.net.ConnectionPool;
import lx.lindx.drive.server.security.AuthProcessor;
import lx.lindx.drive.server.service.UserServiceImpl;
import lx.lindx.drive.server.util.Config;
import lx.lindx.drive.server.util.Util;

/**
 * Server
 */
@Data
public class Server extends Thread {

  private Socket socket;
  private final int PORT;

  private AuthProcessor authProcessor;
  private ConnectionPool connectionPool;

  public Server() {
    this(Config.getPort());
  }

  public Server(final int port) {
    this.PORT = port;
    this.authProcessor = new AuthProcessor(new UserServiceImpl(), this);
    this.connectionPool = new ConnectionPool(authProcessor);
  }

  @Override
  public void run() {

    Util.log(Util.getStr("srv.start") + " -> " + PORT);
    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
      while (true) {
        Util.log(Util.getStr("srv.wait"));
        socket = serverSocket.accept();
        Util.log("Client" + Util.getAddress(socket) + "connected!");
        new Connection(socket, this).start();
      }
    } catch (IOException e) {
    }
  }
}