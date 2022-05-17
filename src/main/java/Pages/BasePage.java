package Pages;

import Utilities.DriverManager;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class BasePage {

        public static WebElement getElement(String element) {
            String elements[] = element.split("£");
            String findLocator = elements[0];
            String elementBy = elements[1];
            WebElement webElement = null;
            switch(findLocator) {
                case "id" :
                    webElement =  DriverManager.getDriver().findElementById(elementBy);
                    break;
                case "className" :
                    webElement = DriverManager.getDriver().findElementByClassName(elementBy);
                    break;
                case "css" :
                    webElement = DriverManager.getDriver().findElementByCssSelector(elementBy);
                    break;
                case "xpath" :
                    webElement = DriverManager.getDriver().findElementByXPath(elementBy);
                    break;
                case "linkText" :
                    webElement = DriverManager.getDriver().findElementByLinkText(elementBy);
                    break;
                case "partialLinkText" :
                    webElement = DriverManager.getDriver().findElementByPartialLinkText(elementBy);
                    break;
                case "Andriod" :
                    // ((AndroidDriver)DriverManager.getDriver()).findElementByAndroidUIAutomator(new)
                    break;
                default:
                    System.out.println("No Element Found");

            }
            return webElement;
        }

        public static void click(WebElement ele){
            try {
                ele.click();
            }catch(ElementClickInterceptedException e){
                e.printStackTrace();
            }
        }

        public static void fillInput(WebElement ele,String text){
            ele.sendKeys(text);
        }


        public static String getCurrentContext() {
            return DriverManager.getDriver().getContext();
        }
        public static void SwitchContext(String PreviousContext) {
            addExplicitWait(3000);
            Set<String> contextNames = DriverManager.getDriver().getContextHandles();
            for (String contextName : contextNames) {
                if(!contextName.equals(PreviousContext)) {


                     DriverManager.getDriver().context("WEBVIEW_chrome");
                }//prints out something like NATIVE_APP \n WEBVIEW_1
            }


        }
         public static void addExplicitWait(int ms) {
             try {
                 Thread.sleep(ms);
             }catch(Exception e){
                 e.printStackTrace();
             }
         }

    }

