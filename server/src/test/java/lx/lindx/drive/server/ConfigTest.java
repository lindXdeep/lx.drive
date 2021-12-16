package lx.lindx.drive.server;

import java.util.Properties;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import lx.lindx.drive.server.util.Config;

/**
 * AppTest
 */
public class ConfigTest {

  private static final String expect_done = "done!";
  private static final String srvprop = "server.properties";
  private static final String cfgprop = "config.properties";
  private static final String srv_param = "srv.prop";
  private static final String cfg_param = "cfg.prop";
  
  @BeforeAll
  public static void init() {
   
  }

  @Test
  void getProp() {

    Properties propServ = Config.getProp(srvprop);
    Properties propConf = Config.getProp(cfgprop);
   
    Assertions.assertEquals(expect_done, propServ.getProperty(srv_param));
    Assertions.assertEquals(expect_done, propConf.getProperty(cfg_param));
  }
}