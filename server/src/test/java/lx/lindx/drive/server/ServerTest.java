package lx.lindx.drive.server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ServerTest {
  
  private static Server server;

  @BeforeAll
  public static void init() {
    server = new Server();
  }
  
  @Test
  public void testMult() {
    Assertions.assertEquals(4, server.mul(2, 2));
  }

  @Test
  public void testClient() {
    Assertions.assertEquals("server run", server.run());
  }
}





  