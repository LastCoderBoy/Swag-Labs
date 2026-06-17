package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import util.JavaScriptUtil;

public class ProductDetailPage extends BasePage {

    private final By productName = By.xpath("//div[@data-test='inventory-item-name']");
    private final By productDescription = By.xpath("//div[@data-test='inventory-item-desc']");
    private final By productPrice = By.xpath("//div[@data-test='inventory-item-price']");
    private final By addToCartButton = By.id("add-to-cart");
    private final By removeButton = By.id("remove");

    public ProductDetailPage(WebDriver driver, JavaScriptUtil jsUtil) {
        super(driver, jsUtil);
    }

    public boolean isProductPageLoaded() {
        return find(productName).isDisplayed();
    }

    public String getProductName(){
        return getText(productName);
    }

    public String getProductDescription(){
        return getText(productDescription);
    }

    public String getProductPrice(){
        return getText(productPrice);
    }

    public boolean isRemoveButtonDisplayed(){
        return find(removeButton).isDisplayed();
    }
}
