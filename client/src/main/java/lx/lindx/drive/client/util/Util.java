package lx.lindx.drive.client.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lx.lindx.drive.client.err.UserDirNotFoundException;

public class Util {

  private static byte[] buffer;
  private static MessageDigest digest;

  private final static Logger LOG = LogManager.getLogger(Util.class.getSuperclass().getName());

  static {
    try {
      digest = MessageDigest.getInstance("SHA3-256");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
  }

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

  public static int byteToInt(byte[] bytes) {
    return (bytes != null || bytes.length == 4) ?

        (int) ((0xFF & bytes[0]) << 24 |
            (0xFF & bytes[1]) << 16 |
            (0xFF & bytes[2]) << 8 |
            (0xFF & bytes[3]) << 0)
        : 0x0;
  }

  public static byte[] intToByte(int i) {
    return new byte[] {
        (byte) ((i >> 24) & 0xFF),
        (byte) ((i >> 16) & 0xFF),
        (byte) ((i >> 8) & 0xFF),
        (byte) ((i >> 0) & 0xFF) };
  }

  public static String byteToStr(byte[] b) {
    return new String(b, 0, b.length);
  }

  public static byte[] strToByte(String str) {
    return str.getBytes();
  }

  public static byte[] objectToBytes(final Object object) {
    try (ByteArrayOutputStream bytes = new ByteArrayOutputStream()) {
      try (ObjectOutputStream obj_out = new ObjectOutputStream(bytes)) {
        obj_out.writeObject(object);
        obj_out.flush();
        return bytes.toByteArray();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    throw new RuntimeException();
  }

  public static Object bytesToObject(final byte[] bytes) {
    try (ByteArrayInputStream bytein = new ByteArrayInputStream(bytes)) {
      try (ObjectInputStream objin = new ObjectInputStream(bytein)) {
        return objin.readObject();
      }
    } catch (ClassNotFoundException | IOException e) {
      Util.log().error(e.getMessage());
    }
    throw new RuntimeException();
  }

  private static String bytesToHex(final byte[] bytes) {

    StringBuilder hexStr = new StringBuilder();

    for (byte b : bytes) {
      String hex = Integer.toHexString(0xff & b);
      hexStr.append(hex.length() == 1 ? '0' : hex);
    }
    return hexStr.toString();
  }

  public static String toHex(final Object o) {
    final byte[] hashbytes = digest.digest(objectToBytes(o));
    return bytesToHex(hashbytes);
  }

  public static String toHex(final String s) {
    final byte[] hashbytes = digest.digest(s.getBytes());
    return bytesToHex(hashbytes);
  }
}
