package lx.lindx.drive.server;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.PreparedStatement;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import lx.lindx.drive.server.model.User;
import lx.lindx.drive.server.model.UserBuilder;
import lx.lindx.drive.server.service.DataBaseService;
import lx.lindx.drive.server.service.IUserService;
import lx.lindx.drive.server.service.UserServiceImpl;
import static lx.lindx.drive.server.util.DbQuery.CREATE_USERS;
import lx.lindx.drive.server.util.Util;

public class UserServiceTest {

  private static User expected_user;
  private static User actual_user;

  private static final String expect_userName = "user";
  private static final String expect_email = "user@user";
  private static final String expect_password = "password";
  private static final String expect_authCode = "somecode";
  private static final String expect_key = "key0192785";

  private static DataBaseService db;
  private static PreparedStatement statement;

  private static IUserService userService;

  @BeforeAll
  public static void init() {

    db = new DataBaseService();

    userService = new UserServiceImpl();

    expected_user = new UserBuilder()
        .setUsername(expect_userName)
        .setEmail(expect_email)
        .setPassword(expect_password)
        .setAuthCode(expect_authCode)
        .setKey(expect_key)
        .build();

  /*   db.connect();

    assertAll(() -> {
      statement = db.prepareStatement(Util.getQuery(CREATE_USERS.query()));
      assertDoesNotThrow(() -> statement.execute());
      statement.close();
    });

    db.disconnect(); */
  }

  @Test
  void userCrud() {

    userService.add(expected_user);
    actual_user = userService.getUserByKey(expect_key);
    assertEquals(expected_user.getEmail(), actual_user.getEmail());

    User new_user = new UserBuilder()
        .setId(1)
        .setUsername(expect_userName)
        .setEmail(expect_email)
        .setPassword("123123123")
        .setAuthCode(expect_authCode)
        .setKey(expect_key)
        .build();

    userService.update(new_user);
  }
}
