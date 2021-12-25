package lx.lindx.drive.server.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import lx.lindx.drive.server.sync.MetaData;

public class Util {

  private final static Logger LOG = LogManager.getLogger(Util.class.getSuperclass().getName());
  private static StringBuilder sb;
  private static MessageDigest sha1; // для пароля
  private static MessageDigest digest; // для снапшотов

  private static byte[] buffer;

  static {
    try {
      sha1 = MessageDigest.getInstance("SHA-1");
      digest = MessageDigest.getInstance("SHA3-256");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
  }

  private static ClassLoader loader;

  public static Logger log() {
    return LOG;
  }

  public static void log(final String msglog) {
    LOG.info(msglog);
  }

  public static void logQuery(final String log) {
    System.out.println(log);
  }

  public static String getStr(final String str) {
    return Config.srv().getProperty(str);
  }

  public static String getQuery(final String query) {
    try {
      return new String(
          Util.class.getClassLoader().getResourceAsStream("db/".concat(query).concat(".sql")).readAllBytes());
    } catch (IOException e) {
      LOG.error(e.getMessage());
    }
    throw new RuntimeException();
  }

  public static String getAddress(Socket socket) {

    sb = new StringBuilder();

    sb.append(getIp(socket));
    sb.append("--> [PORT:");
    sb.append(socket.getPort());
    sb.append("] ");

    return sb.toString();
  }

  public static String getIp(Socket socket) {

    sb = new StringBuilder();
    sb.append(" [IP:");
    sb.append(socket.getLocalAddress());
    sb.append("] ");

    sb.deleteCharAt(5);

    return sb.toString();
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

  public static JSONObject parseCredential(String data) {

    try {
      return (JSONObject) new JSONParser().parse(data);
    } catch (ParseException e) {
      Util.log().error(e.getMessage());
    }
    return null;
  }

  public static String toHash(String str) {

    StringBuilder sbhash = new StringBuilder();

    for (byte b : sha1.digest(str.getBytes()))
      sbhash.append(String.format("%02X", b));

    return sbhash.toString();
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
