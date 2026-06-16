package pages;

import base.BasePage;
import enums.SortOption;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import util.DropdownUtil;
import util.JavaScriptUtil;

import java.util.List;

public class ProductsPage extends BasePage {
    private final By productsHeader = By.cssSelector(".title");
    private final By sortDropdown = By.cssSelector(".product_sort_container");
    private final By productName = By.cssSelector(".inventory_item_name");
    private final By productPrice = By.cssSelector(".inventory_item_price");
    private final By cartBadgeCounter = By.cssSelector(".shopping_cart_badge");

    public ProductsPage(WebDriver driver, JavaScriptUtil jsUtil) {
        super(driver, jsUtil);
    }

    public boolean isProductsHeaderDisplayed() {
        return find(productsHeader).isDisplayed();
    }
    public String getProductsHeaderText() {
        return getText(productsHeader);
    }

    public void selectSortOption(SortOption option){
        WebElement element = find(sortDropdown);
        DropdownUtil.selectByValue(element, option.getValue());
    }

    public int getNumberOfProducts(){
        return findAll(productName).size();
    }

    public List<String> getAllProductNames(){
        List<WebElement> productElements = findAll(productName);
        return productElements
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public List<Double> getAllProductPrices(){
        List<WebElement> productElements = findAll(productPrice);
        return productElements
                .stream()
                .map(element -> {
                    String price = element.getText();
                    return convertToDouble(price);
                })
                .toList();
    }

    private Double convertToDouble(String price){
        return Double.parseDouble(price.replace("$", ""));
    }

    public void clickAddToCartButton(String productName){
        By byProductId = getAddToCartProductId(productName);
        jsUtil.scrollToElementJS(byProductId);
        click(byProductId);
    }

    public void clickRemoveFromCartButton(String productName){
        By byProductId = getRemoveFromCartProductId(productName);
        jsUtil.scrollToElementJS(byProductId);
        click(byProductId);
    }

    public String getAddToCartButtonText(String productName){
        return getText(getAddToCartProductId(productName));
    }

    public String getRemoveFromCartButtonText(String productName){
        return getText(getRemoveFromCartProductId(productName));
    }

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


    // ==================== PRIVATE METHODS ====================

    private By getAddToCartProductId(String productName){
        String buttonId = "add-to-cart-" + productName.toLowerCase().replace(" ", "-");
        return By.id(buttonId);
    }

    private By getRemoveFromCartProductId(String productName){
        String buttonId = "remove-" + productName.toLowerCase().replace(" ", "-");
        return By.id(buttonId);
    }
}
