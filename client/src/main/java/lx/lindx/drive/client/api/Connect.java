package lx.lindx.drive.client.api;

import java.net.Socket;

import lx.lindx.drive.client.net.Connection;
import lx.lindx.drive.client.util.Util;

public class Connect {

  private Connection connection;
  private final String host;
  private final int port;

  public Connect(String host, String port) {
    this.host = host;
    this.port = Integer.parseInt(port);
    this.connection = new Connection(host, this.port);
  }

  public boolean connect() {
    Util.log(Util.getProp("try.connect") + host + ":" + port);
    return connection.connect();
  }

  public boolean disconnet() {
    return false;
  }

}
