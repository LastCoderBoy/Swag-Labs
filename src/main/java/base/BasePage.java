package base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.CartPage;
import pages.ProductDetailPage;
import util.JavaScriptUtil;
import util.WaitUtils;

import java.util.List;

public class BasePage {
    protected WebDriver driver;
    protected JavaScriptUtil jsUtil;

    // shared locators that exist on every page
    private final By cartBadgeCounter = By.cssSelector(".shopping_cart_badge");
    private final By cartIcon = By.cssSelector(".shopping_cart_link");

    public BasePage(WebDriver driver, JavaScriptUtil jsUtil) {
        this.driver = driver;
        this.jsUtil = jsUtil;
    }

    // find By Locator
    protected WebElement find(By locator) {
        return WaitUtils.waitForVisible(driver, locator);
    }

    protected List<WebElement> findAll(By locator) {
        return driver.findElements(locator);
    }

    // Send value to prompts
    protected void set(By target, String value) {
        WebElement element = find(target);
        element.clear();
        element.sendKeys(value);
    }

    // Click on elements
    protected void click(By locator) {
        WaitUtils.waitForClickable(driver, locator).click();
    }

    // Retrieve text from elements
    protected String getText(By locator) {
        return WaitUtils.waitForVisible(driver, locator).getText();
    }

    // ============== COMMON METHODS FOR ALL PAGES =================

    public int getCartBadgeCounter(){
        // Use findElements instead of waiting to check presence without timeout delay
        List<WebElement> badgeElements = driver.findElements(cartBadgeCounter);

        if (badgeElements.isEmpty()) {
            return 0; // Badge doesn't exist means cart is empty
        }

        // Badge exists, now wait for it to be visible and get the count
        try {
            String badgeText = getText(cartBadgeCounter);
            return Integer.parseInt(badgeText);
        } catch (Exception e) {
            return 0;
        }
    }

    public CartPage clickCartButton(){
        jsUtil.scrollToElementJS(cartIcon);
        click(cartIcon);
        return new CartPage(driver, jsUtil);
    }

    public ProductDetailPage openProductDetailPage(String productName){
        By productTitleLocator = getProductTitleLocator(productName);
        jsUtil.scrollToElementJS(productTitleLocator);
        click(productTitleLocator);
        return new ProductDetailPage(driver, jsUtil);
    }

    public By getRemoveFromCartProductId(String productName){
        String buttonId = "remove-" + productName.toLowerCase().replace(" ", "-");
        return By.id(buttonId);
    }

    private By getProductTitleLocator(String productName){
        return By.xpath("//div[@class='inventory_item_name' and text()='" + productName + "']");
    }
}
