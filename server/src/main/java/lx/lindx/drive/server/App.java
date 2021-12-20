package lx.lindx.drive.server;

import lx.lindx.drive.server.core.Server;
import lx.lindx.drive.server.util.Config;

/**
 * App
 */
public class App {

  public static void main(String[] args) {
    
    Server server = new Server(Config.getPort());
    server.start();
  }
}