package lx.lindx.drive.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

import lx.lindx.drive.server.security.Authorize;
import lx.lindx.drive.server.util.Config;
import lx.lindx.drive.server.util.Util;

public class Connection extends Thread {

  private final int bufSize = Config.getBufSize();
  private byte[] buffer;
  private final DataInputStream in;
  private final DataOutputStream out;
  private final Authorize auth;

  public Connection(Socket client, Server server) throws IOException {

    buffer = new byte[bufSize];

    in = new DataInputStream(client.getInputStream());
    out = new DataOutputStream(client.getOutputStream());

    auth = new Authorize(new Protocol());
  }

  @Override
  public void run() {

    try {
      while (!auth.authorize(in.readAllBytes()));
    } catch (IOException e1) {
      e1.printStackTrace();
    }
      
    System.out.println("--------------------------------------");
    System.exit(0);
      
  

    while (true) {

      
    
      System.exit(0);

    }
  }
}
