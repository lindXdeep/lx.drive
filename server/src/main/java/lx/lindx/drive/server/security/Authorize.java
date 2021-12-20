package lx.lindx.drive.server.security;

import org.json.simple.JSONObject;

import lx.lindx.drive.server.core.Server;
import lx.lindx.drive.server.net.Connection;
import lx.lindx.drive.server.net.Protocol;
import lx.lindx.drive.server.util.Config;
import lx.lindx.drive.server.util.Util;

public class Authorize {

  private Protocol protocol;
  private AuthProcessor authProcess;

  private byte[] buffer;

  private String command;
  private String data;
  private int dataSize;
  private Server server;
  private Connection connection;

  public Authorize(Protocol protocol, Server server, Connection connection) {
    this.protocol = protocol;
    this.buffer = new byte[Config.getBufSize()];
    this.server = server;
    this.connection = connection;
  }

  public boolean authorize(byte[] buf) {

    command = Util.byteToStr(protocol.unpacked(buf)[0]).trim();
    data = Util.byteToStr(protocol.unpacked(buf)[2]);
    dataSize = Util.byteToInt(protocol.unpacked(buf)[1]);

    Util.log("get:{ command: `" + command + "`, data: `" + data + "`, size: `" + dataSize + "`byte }");

    if (command.matches("/key")) {

      if (server.getAuthProcessor().enable(data)) {
        connection.send(protocol.packed("/accepted".getBytes(), data.getBytes()));
        return true;
      }
    } else if (command.matches("/auth")) {

      JSONObject tmpUser = Util.parseCredential(data);

      if ((buffer = server.getAuthProcessor().authenticate(tmpUser)).length != 0) { // send [key] or [0]
        connection.send(protocol.packed("/accepted".getBytes(), buffer));
        Util.log("send: " + "/accepted " + Util.byteToStr(buffer));
        return true;
      }

    } else if (command.matches("/new")) {
      // TODOL: add registred new user;
      Util.log().error("registration is not available yet");
      connection.send("registration is not available yet".getBytes());
    }

    connection.send(new byte[0]);
    return false;
  }
}
