package lx.lindx.drive.client;

import lx.lindx.drive.client.api.Auth;
import lx.lindx.drive.client.api.Connect;
import lx.lindx.drive.client.api.Controller;
import lx.lindx.drive.client.cli.Cli;
import lx.lindx.drive.client.err.UserDirNotFoundException;
import lx.lindx.drive.client.net.Protocol;
import lx.lindx.drive.client.util.Util;

/**
 * AppClient
 */
public class App {

  private static Connect connect;
  private static Auth auth;
  private static Controller controller;

  public static void main(String... args) {

    try {
      Util.checkUserSourceDir();
    } catch (UserDirNotFoundException e) {
      Util.createDefaultUserDir();
    }

    connect = new Connect(
        Util.getCfgProp("srv.host"),
        Util.getCfgProp("srv.port"));

    auth = new Auth(new Protocol());
    controller = new Controller(connect, auth);

    new Cli(controller);
  }
}