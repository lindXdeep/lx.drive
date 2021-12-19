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

    // controller

    Auth auth = new Auth(new Protocol());

    connect.send(auth.signin("username", "password"));
    connect.send(auth.signup("username", "email", "password"));

  }
}