package lx.lindx.drive.client;

import lx.lindx.drive.client.api.Auth;
import lx.lindx.drive.client.api.Connect;
import lx.lindx.drive.client.net.Protocol;
import lx.lindx.drive.client.util.Util;

public class Controller {

  private Connect connect;
  private Auth auth;
  private byte[] singleBuff;
  private byte[][] doubleBuff;

  private Protocol protocol;

  public Controller(Connect connect, Auth auth) {
    this.connect = connect;
    this.auth = auth;
    this.protocol = new Protocol();
  }

  public void connect() {
    connect.connect();
  }

  public void sigin(String username, String password) {

    connect.send(auth.signin(username, password));

    // response to get credentials
    if ((singleBuff = connect.read()).length != 0) {
      auth.authenticate(protocol.unpacked(singleBuff));
    }
  }

  public boolean authWithkey() {

    if ((singleBuff = auth.getKey()) != null) {
      connect.send(singleBuff);
    }

    doubleBuff = protocol.unpacked(connect.read());

    if (Util.byteToStr(doubleBuff[0]).startsWith("/accepted")) {
      Util.log("set key: " + Util.byteToStr(doubleBuff[2]));
      auth.logged(doubleBuff[2]);
      return true;
    }
    return false;
  }

  public boolean enterToAccount() {

    System.out.println("log stat:" + auth.isLogged());

    if (auth.isLogged()) { // if key accepted then loged to account

      System.out.println("enter to account: ");

      Thread th = new Thread(() -> {

        while (true) {

          System.out.println(Util.byteToStr(connect.read()));

        }

      });
      th.start();

      return true;
    }
    return false;
  }
}
