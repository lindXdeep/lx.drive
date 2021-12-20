package lx.lindx.drive.client.err;

public class WrongCommandException extends RuntimeException {
  public WrongCommandException(String str) {
    super("Error: No such command exists: " + str);
  }
}
