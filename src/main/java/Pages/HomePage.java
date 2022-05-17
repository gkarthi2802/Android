package Pages;

public class HomePage extends BasePage {

    public String homeIcon = "xpath£//android.widget.FrameLayout[@content-desc=\"Home\"]/android.widget.ImageView";
    public String login = "id£exchange.himalaya.pay:id/btn_login_home";

    public Boolean isHomeIconAvailable() {
        return getElement(homeIcon).isDisplayed();
    }

    public void clickLoginButton(){

        click(getElement(login));
    }

}
