package lx.lindx.drive.server;

import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * App
 */
public class App {

  private static final Logger LOG = LogManager.getLogger(App.class);

  private static Properties properties;
  private static String serverPropertiesFile = "server.properties";

  public static void main(String[] args) {

    System.out.println("start server");

    LOG.debug("test");

    try (InputStream input = App.class.getClassLoader().getResourceAsStream(serverPropertiesFile)) {
      
      

      if (input == null) {
        LOG.info("Unable to find server.properties");

       
        return;

      }

      properties = new Properties();
      properties.load(input);

    } catch (Exception e) {
      // TODO: handle exception
    }

  }
}