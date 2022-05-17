package StepDefinitions;

import Pages.AllPages;
import Pages.BasePage;
import Utilities.DriverManager;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.junit.Assert;

public class HomeSteps extends AllPages {
    public String prevContext;
    public HomeSteps()
    {
        super();
    }

    @Given("^user is on Home Page$")
    public void user_is_on_Home_page() {
       Assert.assertTrue(homepage.isHomeIconAvailable());
    }

    @When("^user clicked on login button$")
    public void user_clocked_on_login_button() {
        prevContext = DriverManager.getDriver().getContext();
        homepage.clickLoginButton();
    }
    @When("^user enter email and password$")
    public void user_enter_email_and_password() {
        BasePage.SwitchContext(prevContext);



    }



}
