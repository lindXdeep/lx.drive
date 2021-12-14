import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import lx.lindx.drive.client.Client;

public class ClientTest {

  private static Client client;

  @BeforeAll
  public static void init() {
    client = new Client();
  }
  
  @Test
  public void testMult() {
    Assertions.assertEquals(4, client.mul(2, 2));
  }

  @Test
  public void testClient() {
    Assertions.assertEquals("client run", client.run());
  }
}
