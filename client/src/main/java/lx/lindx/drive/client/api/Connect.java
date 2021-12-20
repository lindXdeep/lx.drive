package lx.lindx.drive.client.api;

import lx.lindx.drive.client.net.Connection;
import lx.lindx.drive.client.net.Protocol;
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
    return connection.kill();
  }

  public void send(final byte[] bytes) {
    
    System.out.println("---send-----");
    Protocol protocol = new Protocol();
    byte[][] db = protocol.unpacked(bytes);
    System.out.println("com: " + Util.byteToStr(db[0]));
    System.out.println("siz: " + Util.byteToInt(db[1]));
    System.out.println("dta: " + Util.byteToStr(db[2]));
    System.out.println("========");


    connection.send(bytes);
  }

  public byte[] read() {

    byte[] b = connection.read();
    
    System.out.println("---read-----");
    Protocol protocol = new Protocol();
    byte[][] db = protocol.unpacked(b);
    System.out.println("com: " + Util.byteToStr(db[0]));
    System.out.println("siz: " + Util.byteToInt(db[1]));
    System.out.println("dta: " + Util.byteToStr(db[2]));
    System.out.println("========");


    return b;
  }
}
