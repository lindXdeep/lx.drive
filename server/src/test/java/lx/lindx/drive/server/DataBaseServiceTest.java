package lx.lindx.drive.server;

import static lx.lindx.drive.server.util.DbQuery.CREATE_USERS;
import static lx.lindx.drive.server.util.DbQuery.DROP_USERS;
import static lx.lindx.drive.server.util.DbQuery.INSERT_USER;
import static lx.lindx.drive.server.util.DbQuery.UPDATE_USER;
import static lx.lindx.drive.server.util.DbQuery.DELETE_USER;
import static lx.lindx.drive.server.util.DbQuery.SELECT_USER_BY_EMAIL;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import lx.lindx.drive.server.model.User;
import lx.lindx.drive.server.model.UserBuilder;
import lx.lindx.drive.server.service.DataBaseService;
import lx.lindx.drive.server.util.Util;

public class DataBaseServiceTest {

  private static DataBaseService db;
  private static PreparedStatement statement;
  private static ResultSet resultSet;
  private static User expected_user;
  private static User actual_user;

  private static final String expect_userName = "user";
  private static final String expect_email = "user@user";
  private static final String expect_password = "password";
  private static final String expect_authCode = "somecode";
  private static final String expect_key = "key0192785";

  @BeforeAll
  public static void init() {

    db = new DataBaseService();

    db.connect();

    try {
      statement = db.prepareStatement(Util.getQuery(DROP_USERS.query()));
      statement.execute();
      statement.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    db.disconnect();

    expected_user = new UserBuilder()
        .setUsername(expect_userName)
        .setEmail(expect_email)
        .setPassword(expect_password)
        .setAuthCode(expect_authCode)
        .setKey(expect_key)
        .build();
  }

  @Test
  void testInsertUser() {

    db.connect();

    statement = db.prepareStatement(Util.getQuery(CREATE_USERS.query()));
    Assertions.assertDoesNotThrow(() -> statement.execute());
    Assertions.assertAll(() -> statement.close());

    db.disconnect();

    db.connect();

    statement = db.prepareStatement(Util.getQuery(INSERT_USER.query()));

    assertDoesNotThrow(() -> statement.setString(1, expected_user.getUserName()));
    assertDoesNotThrow(() -> statement.setString(2, expected_user.getEmail()));
    assertDoesNotThrow(() -> statement.setString(3, expected_user.getPassword()));
    assertDoesNotThrow(() -> statement.setString(4, expected_user.getAuthCode()));
    assertDoesNotThrow(() -> statement.setString(5, expected_user.getKey()));
    assertDoesNotThrow(() -> statement.executeUpdate());
    assertAll(() -> statement.close());

    db.disconnect();

    db.connect();

    statement = db.prepareStatement(Util.getQuery(SELECT_USER_BY_EMAIL.query()));
    Assertions.assertDoesNotThrow(() -> statement.setString(1, expect_email));
    Assertions.assertDoesNotThrow(() -> resultSet = statement.executeQuery());

    assertAll(() -> {
      if (resultSet.next()) {
        actual_user = new UserBuilder()
            .setId(resultSet.getInt("id"))
            .setUsername(resultSet.getString("userName"))
            .setEmail(resultSet.getString("email"))
            .setPassword(resultSet.getString("password"))
            .setAuthCode(resultSet.getString("authCode"))
            .setKey(resultSet.getString("user_key"))
            .build();
      }
    });

    db.disconnect();

    Assertions.assertEquals(expected_user.getEmail(), actual_user.getEmail());
  }

  @AfterAll
  static void dropTable() {

    db.connect();

    statement = db.prepareStatement(Util.getQuery(DROP_USERS.query()));
    assertDoesNotThrow(() -> statement.execute());
    assertDoesNotThrow(() -> statement.close());

    db.disconnect();
  }
}
