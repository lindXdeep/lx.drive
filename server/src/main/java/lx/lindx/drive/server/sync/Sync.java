package lx.lindx.drive.server.sync;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import static java.nio.file.LinkOption.NOFOLLOW_LINKS;;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import lx.lindx.drive.server.util.Util;

import java.util.Set;

public class Sync implements Serializable {

  private byte[] buffer;
  private String outFileName = ".sync";
  private Path userDir;
  private Path pathToSynsFile;

  private Snapshot snapshot;
  private String last;
  private Set<MetaData> snap;
  private Map<String, Set<MetaData>> history;

  public Sync(final String userdir) {
    this.userDir = Paths.get(userdir);
    this.pathToSynsFile = Paths.get(userdir.toString(), outFileName);
    this.snapshot = new Snapshot(outFileName);

    if (!Files.exists(pathToSynsFile, NOFOLLOW_LINKS)) {
      history = new LinkedHashMap<>();
    } else {
      read();
    }
  }

  public void save() {
    try (FileOutputStream fout = new FileOutputStream(pathToSynsFile.toString())) {
      try (ObjectOutputStream objout = new ObjectOutputStream(fout)) {
        objout.writeObject(history);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void read() {
    try (FileInputStream fin = new FileInputStream(pathToSynsFile.toString())) {
      try (ObjectInputStream objin = new ObjectInputStream(fin)) {
        history = (HashMap<String, Set<MetaData>>) objin.readObject();
      }
    } catch (ClassNotFoundException | IOException e) {
      e.printStackTrace();
    }
  }

  public void delete() {
    try {
      Files.delete(pathToSynsFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void make() {
    snap = snapshot.make(userDir);
    if (snap.size() != 0) {
      history.put(last = snapshot.getHex(), snap);
    }
  }

  public void showSnaps() {

    Iterator<Entry<String, Set<MetaData>>> i = history.entrySet().iterator();

    while (i.hasNext()) {

      Entry<String, Set<MetaData>> e = i.next();

      Util.log("--------------------------------------------------");
      Util.log("snap: " + e.getKey());
      Set<MetaData> files = e.getValue();
      Util.log(String.format("%-65s | %-20s | %-10s | %-2s", "HASH", "LAST MODIFY", "SIZE", "FILE"));
      for (MetaData m : files) {
        Util.log(
            String.format("%-65s | %-15d | %-10d | %-5s", m.getHex(), m.getDatetime(), m.getSize(), m.getFileName()));
      }
      Util.log("--------------------------------------------------");
    }
  }

  public String getHexLastSnapshot() {
    return this.last;
  }

  public Set<MetaData> getlastSnapshot() {
    return snap;
  }

  @Override
  public boolean equals(final Object o) {

    if (o == this)
      return true;
    if (o == null || this.getClass() != o.getClass())
      return false;

    Sync controller = (Sync) o;

    return this.last.equals(controller.getHexLastSnapshot());
  }
}