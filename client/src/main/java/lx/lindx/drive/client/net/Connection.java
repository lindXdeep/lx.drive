package lx.lindx.drive.client.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import lx.lindx.drive.client.err.CantReadBytesExeption;
import lx.lindx.drive.client.err.CantWriteBytesExeption;
import lx.lindx.drive.client.util.Config;
import lx.lindx.drive.client.util.Util;

public class Connection {

  private boolean connected;

  private DataInputStream in;
  private DataOutputStream out;

  private byte[] buffer;
  private int defBuffSize;

  private Socket socket;
  private String host;
  private int port;

  public Connection(String host, int port) {
    this.host = host;
    this.port = port;

    defBuffSize = Config.getBufferSize();
    buffer = new byte[defBuffSize];
  }

  public boolean connect() {

    int i = 5;

    while (i-- > 0 & socket == null) {

      try {
        this.socket = new Socket(host, 8181);

        connected = true;

        if (connected) {
          Util.log("Connection with " + host + " : " + port + " established!");
          this.in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
          this.out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        }

      } catch (IOException e1) {

        Util.progress(String.format(" %s", i > 0 ? "." : ".\n\n"));

        try {
          Thread.sleep(1000);
        } catch (InterruptedException e2) {
          e2.printStackTrace();
        }
      }
    }
    return connected;
  }

  public boolean kill() {
    if (socket != null) {
      try {
        Thread.sleep(1000);
        socket.close();
        connected = false;
        socket = null;
      } catch (InterruptedException | IOException e) {
        Util.log().error(e.getMessage());
      }
    }
    return false;
  }

  public void send(byte[] bytes) {
    try {
      out.write(bytes);
      out.flush();
    } catch (IOException e) {
      throw new CantWriteBytesExeption();
    }
  }

  public byte[] read() {

    allocateBuffer();

    try {
      in.read(buffer);
    } catch (IOException e) {
      throw new CantReadBytesExeption();
    }
    return buffer;
  }

  private void allocateBuffer() {
    allocateBuffer(defBuffSize);
  }

  public void allocateBuffer(final int size) {
    this.buffer = new byte[size];
  }

  public boolean isConnect() {
    return this.connected;
  }

  public byte[] getBuffer() {
    return buffer;
  }
}
