package lx.lindx.drive.client;

import lx.lindx.drive.client.util.Config;
import lx.lindx.drive.client.util.Util;

/**
 * AppClient
 */
public class App {
  
  public static void main(String[] args) {
    System.out.println("run client");

    Util.log("test info log");
    Util.log().error("test error log");
    System.out.println("clt.prop: " + Util.getCltProp("clt.prop"));
    System.out.println("cfg.prop: " + Util.getCfgProp("cfg.prop"));
    System.out.println("cfg.prop: " + Config.getBufferSize());  

  }
}