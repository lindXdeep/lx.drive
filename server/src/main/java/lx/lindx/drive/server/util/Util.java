package lx.lindx.drive.server.util;

import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Util {

  private final static Logger LOG = LogManager.getLogger(Util.class.getSuperclass().getName());
  private static StringBuilder sb;

  public static Logger log() {
    return LOG;
  }

  public static void log(final String msglog) {
    LOG.info(msglog);
  }

  public static String getStr(final String str) {
    return Config.srv().getProperty(str);
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
}
