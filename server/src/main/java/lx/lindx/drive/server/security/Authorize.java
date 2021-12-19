package lx.lindx.drive.server.security;

import lx.lindx.drive.server.Protocol;
import lx.lindx.drive.server.util.Config;
import lx.lindx.drive.server.util.Util;

public class Authorize {

  private Protocol protocol;

  private byte[] buffer;

  private String command;
  private String data;
  private int dataSize;

  public Authorize(Protocol protocol) {
    this.protocol = protocol;
    this.buffer = new byte[Config.getBufSize()];
  }

  public boolean authorize(byte[] buf) {

    command = Util.byteToStr(protocol.unpacked(buf)[0]);
    data = Util.byteToStr(protocol.unpacked(buf)[2]);
    dataSize = Util.byteToInt(protocol.unpacked(buf)[1]);

    Util.log("get command: " + command + " data: " + data + " size: " + dataSize + "byte");

  
    return false;
  }
}
