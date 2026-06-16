package tests;

import base.BaseTest;
import enums.SortOption;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ProductsPage;

import java.util.Comparator;
import java.util.List;

public class ProductTests extends BaseTest {
    private ProductsPage productsPage;

    @BeforeMethod
    public void login() {
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        productsPage = loginPage.clickLoginButton();
    }

    @Test
    public void shouldDisplayProductsHeader_WhenProductsPageIsLoaded() {
        Assert.assertTrue(productsPage.isProductsHeaderDisplayed(), "Products header is not displayed");
        Assert.assertEquals(productsPage.getProductsHeaderText(), "Products", "Products header text is incorrect");
    }

    @Test
    public void shouldHasProducts_WhenProductsPageIsLoaded() {
        productsPage.getNumberOfProducts();
        Assert.assertTrue(productsPage.getNumberOfProducts() > 0, "No products found on the products page");
    }

    @Test
    public void shouldBeSortedInAscOrder_WhenPageIsLoaded() {
        List<String> availableProductNames = productsPage.getAllProductNames();
        List<String> sortedProductNames = availableProductNames.stream().sorted().toList();

        Assert.assertEquals(availableProductNames, sortedProductNames, "Products are not sorted in ascending order");
    }

    @Test
    public void shouldSortAtoZ_WhenNameAtoZFilterIsSelected() {
        productsPage.selectSortOption(SortOption.NAME_A_TO_Z);

        List<String> names = productsPage.getAllProductNames();
        List<String> actualSortedProducts = names.stream().sorted().toList();

        Assert.assertEquals(actualSortedProducts, actualSortedProducts, "Products are not sorted from A to Z order");
    }


    @Test
    public void shouldSortZtoA_WhenNameZtoAFilterIsSelected() {
        productsPage.selectSortOption(SortOption.NAME_Z_TO_A);

        List<String> names = productsPage.getAllProductNames();
        List<String> sorted = names.stream().sorted(Comparator.reverseOrder()).toList();

        Assert.assertEquals(sorted, names, "Products are not sorted from Z to A order");
    }

    @Test
    public void shouldSortLowToHighPrice_WhenPriceLowToHighFilterIsSelected() {
        productsPage.selectSortOption(SortOption.PRICE_LOW_TO_HIGH);
        List<Double> prices = productsPage.getAllProductPrices();
        List<Double> sorted = prices.stream().sorted().toList();

        Assert.assertEquals(sorted, prices, "Products are not sorted from low to high price order");
    }

    @Test
    public void shouldSortHighToLowPrice_WhenPriceHighToLowFilterIsSelected() {
        productsPage.selectSortOption(SortOption.PRICE_HIGH_TO_LOW);
        List<Double> prices = productsPage.getAllProductPrices();
        List<Double> sorted = prices.stream().sorted(Comparator.reverseOrder()).toList();

        Assert.assertEquals(sorted, prices, "Products are not sorted from high to low price order");
    }


    // ====================================
    // Add-To-Cart and Remove Buttons Tests
    // ====================================

    @Test
    public void shouldDisplayRemoveButton_WhenAddToCartButtonIsClicked() {
        String productName = "Sauce Labs Backpack";
        productsPage.clickAddToCartButton(productName);

        String productButtonText = productsPage.getRemoveFromCartButtonText(productName);
        int badgeCounter = productsPage.getCartBadgeCounter();

        Assert.assertEquals(badgeCounter, 1, "Cart badge counter did not update to 1 after adding product to cart");
        Assert.assertEquals(productButtonText, "Remove", "Button text did not change to 'Remove' after adding to cart");
    }

    @Test
    public void shouldDisplayAddToCartButton_WhenRemoveButtonIsClicked() {
        // Setup
        String productName = "Sauce Labs Onesie";
        productsPage.clickAddToCartButton(productName);

        // Guard assertion - verify setup succeeded before testing remove
        Assert.assertEquals(
                productsPage.getRemoveFromCartButtonText(productName),
                "Remove",
                "Precondition failed - Add to Cart did not work"
        );

        productsPage.clickRemoveFromCartButton(productName); // When, we remove it from the cart

        String productButtonText = productsPage.getAddToCartButtonText(productName);

        Assert.assertEquals(productButtonText, "Add to cart", "Button text did not change back to 'Add to cart' after removing from cart");
    }

    @Test
    public void shouldUpdateCartBadgeCounter_WhenProductsAreAddedAndRemoved() {
        String productName1 = "Sauce Labs Backpack";
        String productName2 = "Sauce Labs Fleece Jacket";

        // Add first product to cart
        productsPage.clickAddToCartButton(productName1);
        Assert.assertEquals(productsPage.getCartBadgeCounter(), 1, "Cart badge counter did not update to 1 after adding first product");

        // Add second product to cart
        productsPage.clickAddToCartButton(productName2);
        Assert.assertEquals(productsPage.getCartBadgeCounter(), 2, "Cart badge counter did not update to 2 after adding second product");

        // Remove first product from cart
        productsPage.clickRemoveFromCartButton(productName1);
        Assert.assertEquals(productsPage.getCartBadgeCounter(), 1, "Cart badge counter did not update to 1 after removing first product");

        // Remove second product from cart
        productsPage.clickRemoveFromCartButton(productName2);
        Assert.assertEquals(productsPage.getCartBadgeCounter(), 0, "Cart badge counter did not update to 0 after removing second product");
    }
}
