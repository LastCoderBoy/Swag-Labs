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

}
