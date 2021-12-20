package lx.lindx.drive.server.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import lx.lindx.drive.server.model.User;
import lx.lindx.drive.server.util.Config;
import lx.lindx.drive.server.util.Util;

public class DataBaseService {

  private final String url;
  private final String user;
  private final String pass;

  private Connection connection;

  {
    url = Config.getDbUrl();
    user = Config.getDbUser();
    pass = Config.getDbPass();

    try {
      Class.forName(Config.getDbDriver());
      Util.log(Util.getStr("drvr.success"));
    } catch (ClassNotFoundException e) {
      Util.log().error(e.getMessage());
    }
  }

  public void connect() {
    try {
      connection = DriverManager.getConnection(url, user, pass);
    } catch (SQLException e) {
      Util.log().error(e.getMessage());
    }
  }

  public PreparedStatement prepareStatement(final String sql) {
    try {
      Util.logQuery(sql);
      return connection.prepareStatement(sql);
    } catch (SQLException e) {
      Util.log().error(e.getMessage());
    }
    throw new RuntimeException();
  }

  public void disconnect() {
    try {
      connection.close();
    } catch (SQLException e) {
      Util.log().error(e.getMessage());
    }
  }
}
