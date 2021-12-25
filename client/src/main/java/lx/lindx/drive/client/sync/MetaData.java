package lx.lindx.drive.client.sync;

import java.io.Serializable;
import java.nio.file.Path;

import lx.lindx.drive.client.util.Util;

public class MetaData implements Serializable {

  private Status status; // -1 deleted, 0 - normal, 1 - modyfied, 2 - new

  private final String fileName;
  private final long datetime;
  private final long size;
  private final String hex;

  public MetaData(final Path path, long datetime, long size, final Status status) {

    this.status = status;
    this.fileName = path.toString();
    this.datetime = datetime;
    this.size = size;
    this.hex = Util.toHex(fileName.concat(Long.toString(datetime)).concat(Long.toString(size)));
  }

  @Override
  public int hashCode() {
    int hash = 17;
    hash = hash * 17 + this.fileName.hashCode() + 21;
    hash = hash * 17 + (int) (this.datetime / 2 * 31);
    hash = hash + 17 + (int) (this.size / 2 * 31);
    return hash;
  }

  public String getFileName() {
    return fileName;
  }

  public long getDatetime() {
    return datetime;
  }

  public long getSize() {
    return size;
  }

  public String getHex() {
    return hex;
  }

  @Override
  public String toString() {
    return this.hex;
  }

  public Status getStatus() {
    return this.status;
  }

  public void setStatus(final Status status) {
    this.status = status;
  }

  public byte status() {
    return status.getStatus();
  }
}
