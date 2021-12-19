package lx.lindx.drive.client.api;

import org.json.simple.JSONObject;

import lx.lindx.drive.client.net.Protocol;

public class Auth {

  private Protocol protocol;

  public Auth(Protocol protocol) {
    this.protocol = protocol;
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
}
