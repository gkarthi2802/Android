package Utilities;

import cucumber.api.Scenario;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.apache.commons.lang3.StringUtils;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

public class Start_App  {
    public static AppiumDriverLocalService service;
    public static Logging logging;

    public Start_App(Scenario Scenario){
      logging = new Logging();
      Proxy.loadProperties();
      Start_App.startAppiumServer();
      Start_App.startSession(Proxy.prop.getProperty("device"));

    }


    public static void startAppiumServer() {
      AppiumServiceBuilder builder = new AppiumServiceBuilder();
      builder.usingAnyFreePort();
      builder.withArgument(GeneralServerFlag.ALLOW_INSECURE,"chromedriver_autodownload");
      service = AppiumDriverLocalService.buildService(builder);
      service.start();


  }

  public static void StopAppiumServer(){
        service.stop();
  }
  public static void startSession(String device){
      AppiumDriver driver;
      DesiredCapabilities caps = new DesiredCapabilities();
      String ChromeDir = System.getProperty("user.dir") + "/src/main/resources/drivers";
      String mappingFile = System.getProperty("user.dir") + "/src/main/resources/Mapping.json";
       try {
           if (device.equalsIgnoreCase("IOS")) {
               caps.setCapability("platformName", Proxy.prop.getProperty("platform"));
               caps.setCapability("platformVersion", Proxy.prop.getProperty("osversion"));
               caps.setCapability("deviceName", Proxy.prop.getProperty("devicename"));
               caps.setCapability("automationName", Proxy.prop.getProperty("automationname"));
               caps.setCapability("app", Proxy.prop.getProperty("app"));

               driver = new IOSDriver<MobileElement>(service.getUrl(), caps);
               DriverManager.setDriver(driver);

           } else if (device.equalsIgnoreCase("ANDROID")) {
               caps.setCapability("platformName", Proxy.prop.getProperty("platformName"));
              // caps.setCapability("platformVersion", Proxy.prop.getProperty("platformVersion"));
               caps.setCapability("deviceName", Proxy.prop.getProperty("deviceName"));
               caps.setCapability("automationName", Proxy.prop.getProperty("automationName"));
               caps.setCapability("app", Proxy.prop.getProperty("app"));
               caps.setCapability("chromedriverExecutableDir", ChromeDir);
               caps.setCapability("chromedriverChromeMappingFile",mappingFile);
               driver = new AndroidDriver<MobileElement>(service.getUrl(), caps);
               DriverManager.setDriver(driver);

           }
               DriverManager.getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);



       }catch(Exception e ){
           e.printStackTrace();
       }



  }

    public static void log_file_handler() {
        logging.merge_to_single_log_file();
        logging.delete_temp_dir();
    }

    private void close_log(Scenario scenario) {
        logging.end_log("Finishing scenario >>>>> " + scenario.getName() + "\n" +
                StringUtils.repeat("*", 120) + "\n");
    }

    private void start_log(Scenario scenario) {
        logging.start_log("Starting scenario >>>>> " + scenario.getName());
    }

    public Object load_class(Path path) {
        File file = new File(String.valueOf(path));

        // Convert File to a URL
        URL url = null;
        try {
            url = file.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URL[] urls = new URL[]{url};

        // Create a new class loader with the directory
        ClassLoader cl = new URLClassLoader(urls);

        Class cls = null;
        try {
            cls = cl.loadClass("Constants");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Object object = null;
        try {
            object = cls.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println('y');

        return object;
    }








}
