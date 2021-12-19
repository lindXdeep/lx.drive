package lx.lindx.drive.client.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import lx.lindx.drive.client.net.Protocol;

/**
 * Server Config
 */
public class Config {

  private static Properties clt;
  private static Properties cfg;
  private static final int BUFF;

  static {
    clt = getProp("client.properties");
    cfg = getProp("config.properties");
    BUFF = Integer.parseInt(cfg.getProperty("bufer.size"));
  }

  public static int getBufferSize() {
    return BUFF;
  }

  public static int getCommandBlockSize(){
    return Integer.parseInt(cfg.getProperty("blk.size.commans"));
  }

  public static int getDataSizeBlockSize(){
    return Integer.parseInt(cfg.getProperty("blk.size.datasize"));
  }

  protected static Properties clt() {
    return clt;
  }

  protected static Properties cfg() {
    return cfg;
  }

  public static Properties getProp(String conf) {

    Properties properties = new Properties();

    try (InputStream clnt = Config.class.getClassLoader().getResourceAsStream(conf)) {

      if (clnt == null) {
        throw new RuntimeException();
      }

      properties.load(clnt);
      return properties;

    } catch (IOException e) {
      Util.log(e.getMessage());
    }
    throw new RuntimeException();
  }
}