package lx.lindx.drive.server.security;

import lx.lindx.drive.server.Protocol;
import lx.lindx.drive.server.util.Util;

public class Authorize {

  private Protocol protocol;

  public Authorize(Protocol protocol) {
    this.protocol = protocol;
  }

  public boolean authorize(byte[] buf) {

    byte[][] b = protocol.unpacked(buf);

    System.out.println(Util.byteToStr(b[0])); // command
    System.out.println(Util.byteToInt(b[1])); // data size
    System.out.println(Util.byteToStr(b[2])); // data

    return false;
  }
}
