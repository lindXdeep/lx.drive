package lx.lindx.drive.client;

import java.util.Properties;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import lx.lindx.drive.client.util.Config;

/**
 * ConfigTest
 */
public class ConfigTest {

  private static final String expect_done = "done!";
  private static final String cltprop = "client.properties";
  private static final String cfgprop = "config.properties";
  private static final String clt_param = "clt.prop";
  private static final String cfg_param = "cfg.prop";
  
  @BeforeAll
  public static void init() {
   
  }

  @Test
  void getProp() {

    Properties propClnt = Config.getProp(cltprop);
  //  Properties propConf = Config.getProp(cfgprop);
   
    Assertions.assertEquals(expect_done, propClnt.getProperty(clt_param));
    // Assertions.assertEquals(expect_done, propConf.getProperty(cfg_param));
  }
}