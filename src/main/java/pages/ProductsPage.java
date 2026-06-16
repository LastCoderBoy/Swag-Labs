package pages;

import base.BasePage;
import enums.SortOption;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import util.DropdownUtil;

import java.util.List;

public class ProductsPage extends BasePage {
    private final By productsHeader = By.cssSelector(".title");
    private final By sortDropdown = By.cssSelector(".product_sort_container");
    private final By productName = By.cssSelector(".inventory_item_name");
    private final By productPrice = By.cssSelector(".inventory_item_price");

    public ProductsPage(WebDriver driver) {
        super(driver);
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
}
