package Utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Properties;

public class Proxy {

    public static Properties prop;

    public  static void  loadProperties() {
      try {

          Path path = FileSystems.getDefault().getPath("").toAbsolutePath();

          prop = new Properties();
          InputStream input = new FileInputStream(path +  "/src/main/resources/automate.properties");
          prop.load(input);
      } catch (FileNotFoundException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      }
      }

    }

