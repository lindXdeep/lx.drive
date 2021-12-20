package lx.lindx.drive.server.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import lx.lindx.drive.server.core.Server;
import lx.lindx.drive.server.err.CantReadBytesExeption;
import lx.lindx.drive.server.err.CantWriteBytesExeption;
import lx.lindx.drive.server.security.Authorize;
import lx.lindx.drive.server.util.Config;
import lx.lindx.drive.server.util.Util;

public class Connection extends Thread {

  private final int bufSize = Config.getBufSize();
  private byte[] buffer;
  private final DataInputStream in;
  private final DataOutputStream out;
  private final Authorize auth;
  private Socket client;
  private Server server;
  private String curUser;

  public Connection(Socket client, Server server) throws IOException {

    this.client = client;
    this.server = server;

    in = new DataInputStream(client.getInputStream());
    out = new DataOutputStream(client.getOutputStream());

    auth = new Authorize(new Protocol(), server, this);

    Util.log("Create I/O connection with " + client.toString());
  }

  @Override
  public void run() {

    System.out.println("-------start ---------");

    while (!auth.authorize(read())) {
      Util.log().error(Util.getStr("err.access"));
      kill();
    }

    System.out.println("-----------pass -----------");
    
  
    // Protocol protocol = new Protocol();

    // byte[] buf = this.read();

    // String command = Util.byteToStr(protocol.unpacked(buf)[0]).trim();
    // String data = Util.byteToStr(protocol.unpacked(buf)[2]);
    // int dataSize = Util.byteToInt(protocol.unpacked(buf)[1]);
  
    // System.out.println(command);
    // System.out.println(data);
    // System.out.println(dataSize);

 

   



  //  try {

      // while (!auth.authorize(in.readAllBytes())) {

      //   Util.log().error(Util.getStr("err.access"));

      // }
      
      // else {
      //   Util.log("Auth passed. " + client);
      //   curUser = server.getAuthProcessor().getCurrentUserName();
      //   server.getConnectionPool().add(this);
      // }

    // } catch (IOException e1) {
    //   System.out.println("'///////////////////////'");
    //   e1.printStackTrace();
    // }

    //System.out.println("----------end----------------------------");
  }


  // Not secure
  public byte[] read() throws CantReadBytesExeption {

    allocateBuffer();

    try {
      in.read(buffer);
    } catch (IOException e) {
      throw new CantReadBytesExeption();
    }
    return buffer;
  }

  // Not secure
  public void send(byte[] bytes) {
    try {
      out.write(bytes);
      out.flush();
    } catch (IOException e) {
      throw new CantWriteBytesExeption();
    }
  }

  public void kill() {

    try {
      client.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void allocateBuffer() {
    allocateBuffer(Config.getBufSize());
  }

  public void allocateBuffer(final int size) {
    this.buffer = new byte[size];
  }
}
