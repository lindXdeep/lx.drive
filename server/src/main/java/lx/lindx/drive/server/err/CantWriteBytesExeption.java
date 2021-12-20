package lx.lindx.drive.server.err;

public class CantWriteBytesExeption extends RuntimeException {
  public CantWriteBytesExeption() {
    this("Error: Can't Write Bytes");
  }

  public CantWriteBytesExeption(String str) {
    super(str);
  }
}
