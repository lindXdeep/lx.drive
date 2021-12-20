package lx.lindx.drive.client.err;

import lx.lindx.drive.client.util.Util;

/**
 * UserDirNotFoundException
 */
public class UserDirNotFoundException extends RuntimeException {

  public UserDirNotFoundException() {
    this(Util.getProp("usr.dir.err"));
  }

  public UserDirNotFoundException(String str) {
    super(str);
  }
}