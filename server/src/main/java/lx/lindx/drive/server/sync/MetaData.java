package lx.lindx.drive.server.sync;

import java.io.Serializable;
import java.nio.file.Path;

import lx.lindx.drive.server.util.Util;

public class MetaData implements Serializable {

  private final String fileName;
  private final long datetime;
  private final long size;
  private final String hex;

  public MetaData(final Path path, long datetime, long size) {

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
}
