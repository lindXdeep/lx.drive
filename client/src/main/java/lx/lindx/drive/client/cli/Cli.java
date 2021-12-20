package lx.lindx.drive.client.cli;

import java.util.Scanner;

import lx.lindx.drive.client.api.Controller;
import lx.lindx.drive.client.err.WrongCommandException;
import lx.lindx.drive.client.util.Util;

public class Cli {

  private Controller controller;
  private Scanner cl;

  public Cli(Controller controller) {
    this.controller = controller;
    this.cl = new Scanner(System.in);

    console();
  }

  private void console() {

    while (true) {
      printCur();
      while (cl.hasNext()) {
        try {
          execute(cl.nextLine().trim());
          printCur();
        } catch (WrongCommandException e) {
          Util.log().error(e.getMessage());
          printCur();
        }
      }
    }
  }

  private void execute(String command) {

    if (command.matches("/help") || command.matches("^0")) {
      System.out.printf("\n" + Util.getProp("help") + "\n");
    } else if (command.matches("^/connect") || command.matches("^1")) {
      controller.connect();
    } else if (command.matches("^/signin\\s[a-zA-Z]{3,64}\\s[a-zA-Z0-9]{3,64}$")) {
      controller.sigin(command.split("\\s")[1], command.split("\\s")[2]);
      controller.enterToAccount();
    } else if (command.matches("^/key") || command.matches("^2")) {
      controller.authWithkey();
      controller.enterToAccount();
    }
  }

  private void printCur() {
    System.out.print("::> ");
  }
}