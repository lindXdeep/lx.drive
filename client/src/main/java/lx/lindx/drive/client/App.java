package lx.lindx.drive.client;

import lx.lindx.drive.client.api.Auth;
import lx.lindx.drive.client.api.Connect;
import lx.lindx.drive.client.err.UserDirNotFoundException;
import lx.lindx.drive.client.net.Protocol;
import lx.lindx.drive.client.util.Util;

/**
 * AppClient
 */
public class App {

  private static Connect connect;
  private static Controller controller;
  private static Auth auth;


  public static void main(String... args) {

    try {
      Util.checkUserSourceDir();
    } catch (UserDirNotFoundException e) {
      Util.createDefaultUserDir();
    }

    connect = new Connect(
        Util.getCfgProp("srv.host"),
        Util.getCfgProp("srv.port"));

    // controller
    controller = new Controller(connect, new Auth(new Protocol()));

    System.out.println("----connect-----");
    controller.connect();

    System.out.println("----sigin-----");
    controller.sigin(args[0], args[1]);

    System.out.println("----authWithkey-----");
    controller.authWithkey();

    System.out.println("----enterToAccount-----");
    controller.enterToAccount();
  }
}