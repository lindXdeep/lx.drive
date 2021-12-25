package lx.lindx.drive.server.core;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import lx.lindx.drive.server.sync.MetaData;
import lx.lindx.drive.server.sync.Sync;
import lx.lindx.drive.server.util.Util;

public class Controller {

  private byte[][] doubleBuffer;
  private Protocol protocol;
  private String command;
  private byte[] data;
  private Connection connection;

  public Controller(Server server, Connection connection) {
    protocol = new Protocol();
    this.connection = connection;
  }

  public void proccess(byte[] buffer) {

    doubleBuffer = protocol.unpacked(buffer);
    command = Util.byteToStr(doubleBuffer[0]);

    System.out.println("get:" + command);

    if (command.startsWith("/sync")) {

     
    Set<MetaData> o = (HashSet) Util.bytesToObject(doubleBuffer[2]);

     System.out.println(o);

     //   System.out.println("------_мы туту 2");

        // Iterator<MetaData> i = snapshot.iterator();

        // while (i.hasNext()) {
        //   System.out.println(":  " + i.next().getFileName());
        // }

     

      System.exit(0);

      // connection.send(protocol.packed("i'm get /syns commsnd".getBytes(), new
      // byte[0]));
    }

  }

}
