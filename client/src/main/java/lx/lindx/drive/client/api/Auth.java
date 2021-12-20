package lx.lindx.drive.client.api;

import org.json.simple.JSONObject;

import lx.lindx.drive.client.net.Protocol;
import lx.lindx.drive.client.util.Util;

public class Auth {

  private UserCredential credential;
  private Protocol protocol;
  private byte[] key;
  private boolean logginStatus;

  public Auth(Protocol protocol) {
    this.protocol = protocol;
    this.credential = new UserCredential();
  }

  public byte[] key(final byte[] key) {

    return protocol.packed("/key".getBytes(), key);
  }

  public byte[] signup(final String username, final String email, final String password) {

    JSONObject user_credentinal = new JSONObject();
    user_credentinal.put("username", username);
    user_credentinal.put("email", email);
    user_credentinal.put("password", password);

    return protocol.packed("/new".getBytes(), user_credentinal.toString().getBytes());
  }

  public byte[] signin(final String username, final String password) {

    JSONObject user_credentinal = new JSONObject();
    user_credentinal.put("username", username);
    user_credentinal.put("password", password);

    return protocol.packed("/auth".getBytes(), user_credentinal.toString().getBytes());
  }

  public boolean authenticate(byte[][] acceept) {
    if (Util.byteToStr(acceept[0]).startsWith("/accepted")) {
      credential.saveKey(key = acceept[2]);
      Util.log("get and save Key: " + Util.byteToStr(key));
      logged(key);
      return true;
    }
    return false;
  }

  public byte[] getKey() {
    if (credential.isKeyexist()) {
      return protocol.packed("/key".getBytes(), credential.readKey());
    }
    return null;
  }

  public void logged(byte[] key) {
    this.key = key;
    logginStatus = true;
  }

  public boolean isLogged() {
    return logginStatus;
  }
}
