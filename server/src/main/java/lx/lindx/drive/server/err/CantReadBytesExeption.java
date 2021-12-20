package lx.lindx.drive.server.err;

public class CantReadBytesExeption extends RuntimeException {

  public CantReadBytesExeption() {
    this("Error: Can't Read Bytes");
  }

  public CantReadBytesExeption(String str) {
    super(str);
  }
}
