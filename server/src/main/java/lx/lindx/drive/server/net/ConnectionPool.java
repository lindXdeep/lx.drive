package lx.lindx.drive.server.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lx.lindx.drive.server.security.AuthProcessor;
import lx.lindx.drive.server.util.Util;

public class ConnectionPool {

  private AuthProcessor authProcessor;
  private Map<String, List<Connection>> connections = new HashMap<>();

  public ConnectionPool(AuthProcessor authProcessor) {
    this.authProcessor = authProcessor;
  }

  public void add(Connection connection) {

    String username = authProcessor.getCurrentUserName();
    
    if (!contains(username))
      connections.put(username, new ArrayList<Connection>());

    connections.get(username).add(connection);

    System.out.println("username:" + username );

    Util.log("@".concat(username) + " - Online!");
  }

  public List<Connection> getConnectionByUsername(String username) {
    return connections.get(username);
  }

  public void killAllUsersConnections(String username) {
    for (Connection c : getConnectionByUsername(username)) {
      c.kill();
    }
  }

  // ------ utils ------
  public boolean contains(String username) {
    return connections.containsKey(username);
  }
}
