package lx.lindx.drive.server;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.DriverManager;
import java.sql.SQLException;
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

  private static final String DB_URL = Config.getDbUrl();
  private static final String DB_USER = Config.getDbUser();
  private static final String DB_PASS = Config.getDbPass();
  private static final String DB_DRIVER = Config.getDbDriver();

  @BeforeAll
  public static void init() {

    try {
      Class.forName(DB_DRIVER);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testGetProp() {

    Properties propServ = Config.getProp(srvprop);
    Properties propConf = Config.getProp(cfgprop);

    Assertions.assertEquals(expect_done, propServ.getProperty(srv_param));
    Assertions.assertEquals(expect_done, propConf.getProperty(cfg_param));
  }

  @Test
  void testConnectDataBase() {
    assertAll(() -> DriverManager.getConnection(DB_URL, DB_USER, DB_PASS));
  }

  @Test
  void testConnectDataBaseWrongUser() {
    assertThrows(SQLException.class, () -> DriverManager.getConnection(DB_URL, "userothrer", DB_PASS));
  }
}