package lx.lindx.drive.client.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

  public static String getCltProp(final String string) {
    return Config.clt().getProperty(string);
  }
}
