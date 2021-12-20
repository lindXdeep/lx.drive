package lx.lindx.drive.server.security;

import java.util.Arrays;

import org.json.simple.JSONObject;

import lx.lindx.drive.server.core.Server;
import lx.lindx.drive.server.model.User;
import lx.lindx.drive.server.service.IUserService;
import lx.lindx.drive.server.service.UserServiceImpl;
import lx.lindx.drive.server.util.Util;

/**
 * AuthProcessor
 */
public class AuthProcessor {

  private byte[] key = new byte[0];
  private String username;

  private IUserService userService;
  private Server server;

  public AuthProcessor(UserServiceImpl userService, Server server) {
    this.userService = userService;
    this.server = server;
  }

  public byte[] authenticate(final JSONObject tmpUser) {

    User user = null;
    String u = (String) tmpUser.get("username");

    if ((user = userService.getUserByName(u)) != null) {
      if (user.getPassword().equals(Util.toHash((String) tmpUser.get("password")))) {
        Util.log("Auth: " + (String) tmpUser.get("username") + " / " + user.getEmail());
        return user.getKey().getBytes();
      }
    }
    return new byte[0];
  }

  public char[] getAuthCodeAnd(JSONObject tmpUser) {
    return null;
  }

  public boolean enable(final String key) {

    User user = null;

    if ((user = userService.getUserByKey(key)) != null) {
      Util.log("Login: " + user.getUserName() + " / " + user.getEmail());
      this.key = key.getBytes();
      return true;
    }
    return false;
  }

  public void disable() {
    key = new byte[0];
    username = null;
  }

  public String getCurrentUserName() {

    System.out.println("user:" + username);
    System.out.println("key:" + key.length);

    if (username == null && key.length != 0) {
      username = userService.getUserByKey(Util.byteToStr(key)).getUserName();
    }

    return username;
  }

  public User getUserByUserName(String username) {
    return userService.getUserByName(username);
  }

  public User getUserIfPasswordValid(final String password) {

    User user = userService.getUserByName(username);

    if (username != null && user.getPassword().equals(Util.toHash(password)))
      return user;
    return null;
  }

  public boolean isKeyExist() {

    if (key.length != 0)
      return true;
    else
      return false;
  }

  public boolean isKeyEquals(byte[] reciveKey) {

    if (Arrays.equals(reciveKey, key)) {
      return true;
    } else {
      disable();
      return false;
    }
  }
}