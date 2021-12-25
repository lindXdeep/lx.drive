package lx.lindx.drive.client.sync;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;

import static lx.lindx.drive.client.sync.Status.*;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import lx.lindx.drive.client.util.Util;

public class Snapshot implements Serializable {

  private Set<MetaData> pFiles; // список файлов
  private String hash;
  private String syncFile;
  private String userDir;

  public Snapshot(final String syncFile) {
    pFiles = new HashSet<>();
    this.syncFile = syncFile;
  }

  public Set<MetaData> make(final Path userDir) {

    this.userDir = userDir.toString();

    walkDir(userDir);

    StringBuilder sb = new StringBuilder();
    Iterator<MetaData> i = pFiles.iterator();

    while (i.hasNext())
      sb.append(i.next());

    hash = Util.toHex(sb.toString());
    return pFiles;
  }

  public Iterator<MetaData> iterator() {
    return pFiles.iterator();
  }

  private void walkDir(final Path path) {

    try (DirectoryStream<Path> dir = Files.newDirectoryStream(path)) {

      Iterator<Path> i = dir.iterator();

      while (i.hasNext()) {

        Path p = i.next();
        if (!Files.isDirectory(p, NOFOLLOW_LINKS) && !p.equals(Paths.get(userDir, syncFile))) {

          String fullpath = p.toString();
          Path relative = Paths.get(fullpath.substring(userDir.length()));
          long lastModified = new File(fullpath).lastModified();
          long size = (long) Files.getAttribute(p, "size", NOFOLLOW_LINKS);

          pFiles.add(new MetaData(relative, lastModified, size, NORMAL));

        } else {
          if (!p.equals(Paths.get(userDir, syncFile))) {
            walkDir(p);
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String toString() {
    return this.hash;
  }

  public String getHex() {
    return this.hash;
  }
}
