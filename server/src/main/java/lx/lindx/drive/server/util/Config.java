package lx.lindx.drive.server.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Server Config
 */
public class Config {

  private static Properties srv;
  private static Properties cfg;
  private static final int PORT;
  private static final int BUFF;

  static {
    srv = getProp("server.properties");
    cfg = getProp("config.properties");
    PORT = Integer.parseInt(cfg.getProperty("server.port"));
    BUFF = Integer.parseInt(cfg.getProperty("bufer.size"));
  }

  public static int getPort() {
    return (PORT > 0 && PORT <= 65535) ? PORT : 0;
  }

  public static int getBufSize() {
    return BUFF;
  }

  protected static Properties srv() {
    return srv;
  }

  protected static Properties cfg() {
    return cfg;
  }

  public static Properties getProp(String conf) {

    Properties properties = new Properties();

    try (InputStream serv = Config.class.getClassLoader().getResourceAsStream(conf)) {

      if (serv == null) {
        throw new RuntimeException();
      }

      properties.load(serv);
      return properties;

    } catch (IOException e) {
      Util.log(e.getMessage());
    }
    throw new RuntimeException();
  }
}