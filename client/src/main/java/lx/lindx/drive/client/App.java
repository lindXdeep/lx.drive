package lx.lindx.drive.client;

import lx.lindx.drive.client.api.Connect;
import lx.lindx.drive.client.err.UserDirNotFoundException;
import lx.lindx.drive.client.net.Connection;
import lx.lindx.drive.client.util.Util;

/**
 * AppClient
 */
public class App {

  private static Connect connect;

  /**
   * syns <source> to server
   * 
   * @param args
   */
  public static void main(String... args) {

    try {
      Util.checkUserSourceDir();
    } catch (UserDirNotFoundException e) {
      Util.createDefaultUserDir();
    }

    connect = new Connect(
        Util.getCfgProp("srv.host"),
        Util.getCfgProp("srv.port"));

    connect.connect();
  }
}