import Utilities.*;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Hooks {

  public static Start_App startApp;


 @Before
 public  void beforeTest(Scenario scenario){

     System.out.println("Starting Test!!!");
     startApp = new Start_App(scenario);

 }


@After
 public void AfterSuite(){
    DriverManager.getDriver().quit();
}





}
