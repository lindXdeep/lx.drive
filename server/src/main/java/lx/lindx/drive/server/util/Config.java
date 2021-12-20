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

  private static final String DB_URL;
  private static final String DB_PARAM;
  private static final String DB_USER;
  private static final String DB_PASS;
  private static final String DB_DRIVER;

  static {
    srv = getProp("server.properties");
    cfg = getProp("config.properties");

    PORT = Integer.parseInt(cfg.getProperty("server.port"));
    BUFF = Integer.parseInt(cfg.getProperty("bufer.size"));

    DB_URL = cfg.getProperty("db.url");
    DB_PARAM = cfg.getProperty("db.param");
    DB_USER = cfg.getProperty("db.user");
    DB_PASS = cfg.getProperty("db.pass");
    DB_DRIVER = cfg.getProperty("db.driver");
  }

  public static int getPort() {
    return (PORT > 0 && PORT <= 65535) ? PORT : 0;
  }

  public static int getBufSize() {
    return BUFF;
  }

  public static String getDbUrl() {
    return DB_URL + DB_PARAM;
  }

  public static String getDbUser() {
    return DB_USER;
  }

  public static String getDbPass() {
    return DB_PASS;
  }

  public static String getDbDriver() {
    return DB_DRIVER;
  }

  public static int getCommandBlockSize(){
    return Integer.parseInt(cfg.getProperty("blk.size.commans"));
  }

  public static int getDataSizeBlockSize(){
    return Integer.parseInt(cfg.getProperty("blk.size.datasize"));
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