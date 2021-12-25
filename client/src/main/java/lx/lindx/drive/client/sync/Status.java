package lx.lindx.drive.client.sync;

public enum Status {

  DELETED((byte) -1),
  MODYFIED((byte) 1),
  NORMAL((byte) 0),
  NEW((byte) 2);

  private final byte status;

  Status(final byte status) {
    this.status = status;
  }

  public byte getStatus() {
    return this.status;
  }
}
