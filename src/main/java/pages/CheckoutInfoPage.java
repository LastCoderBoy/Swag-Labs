package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import util.JavaScriptUtil;

public class CheckoutInfoPage extends BasePage {

    private final By checkoutInfoHeader = By.cssSelector(".title");
    private final By checkoutInfoForm = By.cssSelector(".checkout_info");

    public CheckoutInfoPage(WebDriver driver, JavaScriptUtil jsUtil) {
        super(driver, jsUtil);
    }

    public String getCheckoutInfoHeaderText(){
        return getText(checkoutInfoHeader);
    }

    public boolean isCheckoutInfoPageLoaded(){
        return isCheckoutInfoHeaderDisplayed() && isCheckoutInfoFormDisplayed();
    }



    private boolean isCheckoutInfoHeaderDisplayed() {
        return find(checkoutInfoHeader).isDisplayed();
    }

    private boolean isCheckoutInfoFormDisplayed() {
        return find(checkoutInfoForm).isDisplayed();
    }
}
