package lx.lindx.drive.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import lx.lindx.drive.server.util.Config;

public class Connection extends Thread {

  private final int buffer_size = Config.getBufferSize();
  private byte[] buffer;
  private final DataInputStream in;
  private final DataOutputStream out;

  public Connection(Socket client, Server server) throws IOException {
    in = new DataInputStream(client.getInputStream());
    out = new DataOutputStream(client.getOutputStream());
    
  }

  @Override
  public void run() {
    
  }
}
