package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import util.JavaScriptUtil;

import java.util.List;

public class CartPage extends BasePage {
    private final By cartPageHeader = By.cssSelector(".title");
    private final By cartItems = By.cssSelector(".cart_item");
    private final By continueShoppingButton = By.id("continue-shopping");
    private final By checkoutButton = By.id("checkout");
    private final By productsLocator = By.cssSelector(".inventory_item_name");

    public CartPage(WebDriver driver, JavaScriptUtil jsUtil) {
        super(driver, jsUtil);
    }

    public boolean isCartPageHeaderDisplayed() {
        return find(cartPageHeader).isDisplayed();
    }

    public int getNumberOfItemsInCart() {
        return findAll(cartItems).size();
    }

    public ProductsPage clickContinueShoppingButton(){
        jsUtil.scrollToElementJS(continueShoppingButton);
        click(continueShoppingButton);
        return new ProductsPage(driver, jsUtil);
    }

    public CheckoutInfoPage clickCheckoutButton(){
        jsUtil.scrollToElementJS(checkoutButton);
        click(checkoutButton);
        return new CheckoutInfoPage(driver, jsUtil);
    }

    public void clickRemoveButton(String productName){
        By byProductId = getRemoveFromCartProductId(productName);
        jsUtil.scrollToElementJS(byProductId);
        click(byProductId);
    }

    public boolean isItemDisplayed(String productName) {
        List<WebElement> productsInCart = findAll(productsLocator);
        return productsInCart
                .stream()
                .anyMatch(product -> product.getText().equals(productName));
    }

}
