package lx.lindx.drive.server.util;

import java.io.IOException;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Util {

  private final static Logger LOG = LogManager.getLogger(Util.class.getSuperclass().getName());
  private static StringBuilder sb;

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
}
