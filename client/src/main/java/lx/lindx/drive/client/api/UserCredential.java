package lx.lindx.drive.client.api;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import lx.lindx.drive.client.util.Util;

public class UserCredential {

  private byte[] key;

  String keyName = "key";

  public UserCredential() {
    if (isKeyexist()) {
      this.key = readKey();
    }
    key = new byte[0];
  }

  public boolean isKeyexist() {
    return readKey() != null;
  }

  public byte[] readKey() {
    try (DataInputStream dinkey = new DataInputStream(new FileInputStream(keyName))) {
      return dinkey.readAllBytes();
    } catch (IOException e) {
      Util.log().error("Auth key not exist.\n");
    }
    return null;
  }

  public void saveKey(byte[] key) {
    try (DataOutputStream doutkey = new DataOutputStream(new FileOutputStream(keyName))) {
      doutkey.write(key);
      doutkey.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void destroyKey() {
    if (isKeyexist()) {
      key = new byte[0];
      try {
        Files.delete(Paths.get(keyName));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
