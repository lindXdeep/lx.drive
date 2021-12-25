package lx.lindx.drive.client.sync;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static lx.lindx.drive.client.sync.Status.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lx.lindx.drive.client.util.Util;

import java.util.Set;

public class Sync implements Serializable {

  private byte[] buffer;
  private String outFileName = ".sync";
  private Path userDir;
  private Path pathToSynsFile;

  private Snapshot snapshot;

  private String last_hex;
  private Set<MetaData> last_snap;
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

        List<Map.Entry<String, Set<MetaData>>> history_last = new ArrayList(history.entrySet());
        Entry<String, Set<MetaData>> snap = history_last.get(history.size() - 1);

        last_snap = snap.getValue();
        last_hex = snap.getKey();
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

    last_snap = snapshot.make(userDir);
    last_hex = snapshot.getHex();

    if (history.size() < 1 && last_snap.size() != 0) {

      history.put(last_hex, last_snap);
    } else if (history.size() >= 1 && last_snap.size() != 0 && !history.containsKey(last_hex)) {

      // проверяем статус найденных файлов на основе истори
      // если истории нет то все файлы новые (по умолчанию)

      List<Map.Entry<String, Set<MetaData>>> history_prev = new ArrayList(history.entrySet());
      Set<MetaData> prev = history_prev.get(history.size() - 1).getValue();

      Iterator<MetaData> t = last_snap.iterator();
      Iterator<MetaData> p = prev.iterator();

      while (t.hasNext()) {
        MetaData mt = t.next();

        p = prev.iterator();
        while (p.hasNext()) {
          MetaData mp = p.next();

          if (mt.getFileName().equals(mp.getFileName()) && (mt.getHex().equals(mp.getHex()))) {
            mt.setStatus(mp.getStatus() != NORMAL ? mp.getStatus() : NORMAL);
            break;
          } else if (mt.getFileName().equals(mp.getFileName()) && !(mt.getHex().equals(mp.getHex()))) {
            mt.setStatus(MODYFIED);
            break;
          } else if (!p.hasNext() && (mt.getFileName() != mp.getFileName())) {
            mt.setStatus(NEW);
          }
        }
      }

      // проверка на удаленные файлы

      p = prev.iterator();
      while (p.hasNext()) {
        MetaData mp = p.next();

        t = last_snap.iterator();
        while (t.hasNext()) {
          MetaData mt = t.next();

          if (mp.getFileName().equals(mt.getFileName())) {
            break;
          } else if (!t.hasNext() && !(mp.getFileName().equals(mt.getFileName()))) {
            mp.setStatus(DELETED);
            last_snap.add(mp);
          }
        }
      }
      history.put(last_hex, last_snap);
    }
  }

  public void showSnaps() {

    Iterator<Entry<String, Set<MetaData>>> i = history.entrySet().iterator();

    while (i.hasNext()) {

      Entry<String, Set<MetaData>> e = i.next();

      System.out.println("--------------------------------------------------");
      System.out.println("snap: " + e.getKey());
      Set<MetaData> files = e.getValue();
      System.out.println(
          String.format("%-65s | %-5s | %-15s | %-10s | %-5s", "HASH", "STATUS", "LAST MODIFY", "SIZE", "FILE"));
      for (MetaData m : files) {
        System.out.println(
            String.format("%-65s | %-6d | %-15d | %-10s | %s", m.getHex(), m.status(), m.getDatetime(), m.getSize(),
                m.getFileName()));
      }
      System.out.println("--------------------------------------------------\n");
    }
  }

  public String getHexLastSnapshot() {
    return this.last_hex;
  }

  public Set<MetaData> getlastSnapshot() {
    return last_snap;
  }

  @Override
  public boolean equals(final Object o) {

    if (o == this)
      return true;
    if (o == null || this.getClass() != o.getClass())
      return false;

    Sync controller = (Sync) o;

    return this.last_hex.equals(controller.getHexLastSnapshot());
  }
}