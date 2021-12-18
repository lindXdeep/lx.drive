package lx.lindx.drive.client.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lx.lindx.drive.client.err.UserDirNotFoundException;

public class Util {

  private final static Logger LOG = LogManager.getLogger(Util.class.getSuperclass().getName());

  public static Logger log() {
    return LOG;
  }

  public static void log(final String msglog) {
    LOG.info(msglog);
  }

  public static String getCfgProp(final String config) {
    return Config.cfg().getProperty(config);
  }

  public static String getProp(final String string) {
    return Config.clt().getProperty(string);
  }

  public static void printCursor() {
    System.out.print(Util.getProp("cursor"));
  }

  public static void checkUserSourceDir() throws UserDirNotFoundException {

    Path userDir = Paths.get(Util.getCfgProp("src.dir"));

    if (!Files.exists(userDir, LinkOption.NOFOLLOW_LINKS) || !Files.isDirectory(userDir, LinkOption.NOFOLLOW_LINKS)) {
      LOG.error(Util.getProp("usr.dir.err"));
      throw new UserDirNotFoundException();
    } else {
      LOG.info("User directory is: " + userDir.toAbsolutePath());
    }
  }

  public static void createDefaultUserDir() {
    try {
      Path path = Paths.get(Util.getCfgProp("src.dir"));

      if (path.toString().length() != 0) {
        Files.createDirectories(path);
      } else {
        Files.createDirectory(Paths.get("userDir"));
      }
    } catch (IOException e) {
      Util.log().error(e.getMessage());
    }
  }

  public static void progress(String progress) {
    System.out.print(progress);
  }
}
