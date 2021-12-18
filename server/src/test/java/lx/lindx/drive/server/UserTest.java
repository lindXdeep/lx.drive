package lx.lindx.drive.server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lx.lindx.drive.server.model.User;
import lx.lindx.drive.server.model.UserBuilder;

public class UserTest {

  private static User user1;
  private static User user2;
  private static UserBuilder builder;

  private static final long expect_id = 00010020000300001L;
  private static final String expect_userName = "user";
  private static final String expect_email = "user@user";
  private static final String expect_password = "password";
  private static final String expect_authCode = "somecode";
  private static final String expect_key = "key0192785";

  @BeforeAll
  public static void init() {

    user1 = new UserBuilder()
        .setId(expect_id)
        .setUsername(expect_userName)
        .setEmail(expect_email)
        .setPassword(expect_password)
        .setAuthCode(expect_authCode)
        .setKey(expect_key)
        .build();

    user2 = new UserBuilder()
        .setId(expect_id)
        .setUsername(expect_userName)
        .setEmail(expect_email)
        .setPassword(expect_password)
        .setAuthCode(expect_authCode)
        .setKey(expect_key)
        .build();
  }

  @Test
  @DisplayName("Start User testing")
  public void testUser() {
    Assertions.assertTrue(user1.equals(user2));
  }
}
