package lx.lindx.drive.server;

import java.nio.ByteBuffer;
import java.util.Arrays;

import lx.lindx.drive.server.util.Config;
import lx.lindx.drive.server.util.Util;

public class Protocol {

  // blocksize for commands
  private int comm_blk;

  // blocksize for datasize
  private int data_blk;

  // point start data block
  private int zero_data_idx;

  public Protocol() {
    this.comm_blk = Config.getCommandBlockSize();
    this.data_blk = Config.getDataSizeBlockSize();
    this.zero_data_idx = this.comm_blk + this.data_blk;
  }

  public byte[] packed(final byte[] command, final byte[] data) {

    ByteBuffer request;

    request = ByteBuffer.allocate(zero_data_idx + data.length);
    request.put(command);
    request.put(comm_blk, Util.intToByte(data.length));
    request.put(zero_data_idx, data);

    return request.array();
  }

  public byte[][] unpacked(byte[] buf) {

    byte[] command = Arrays.copyOfRange(buf, 0, comm_blk);
    byte[] datasize = Arrays.copyOfRange(buf, comm_blk, zero_data_idx);
    byte[] data = Arrays.copyOfRange(buf, zero_data_idx, zero_data_idx + Util.byteToInt(datasize));
    return new byte[][] { command, datasize, data };
  }
}
