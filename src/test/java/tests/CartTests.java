package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CheckoutInfoPage;
import pages.ProductDetailPage;
import pages.ProductsPage;
import testdata.TestData;

public class CartTests extends BaseTest {

    private ProductsPage productsPage;
    private CartPage cartPage;

    @BeforeMethod
    public void cartSetup() {
        loginPage.enterUsername(TestData.Credentials.STANDARD_USER);
        loginPage.enterPassword(TestData.Credentials.PASSWORD);
        productsPage = loginPage.clickLoginButton();

        // Add Some Products to Cart
        productsPage.clickAddToCartButton(TestData.Products.BIKE_LIGHT);
        productsPage.clickAddToCartButton(TestData.Products.FLEECE_JACKET);
        productsPage.clickAddToCartButton(TestData.Products.ONESIE);

        // Navigate to Cart
        cartPage = productsPage.clickCartButton();
    }

    // Number of items in Cart = Cart Badge Counter
    @Test
    public void shouldMatchCartBadgeCounter_WhenMultipleItemsAreAddedToCart() {
        // Given & When
        int numberOfItemsInCart = cartPage.getNumberOfItemsInCart();
        int totalBadgeCount = cartPage.getCartBadgeCounter();

        // Then
        Assert.assertEquals(numberOfItemsInCart, totalBadgeCount, "Number of items in cart does not match the cart badge counter");
    }

    @Test
    public void shouldGoToProductsPage_WhenContinueShoppingButtonIsClicked() {
        // When
        productsPage = cartPage.clickContinueShoppingButton();

        // Then
        Assert.assertTrue(productsPage.isProductsHeaderDisplayed(), "Products header is not displayed");
        Assert.assertEquals(productsPage.getProductsHeaderText(), "Products", "Products header text is incorrect");
        Assert.assertTrue(productsPage.getNumberOfProducts() > 0, "No products found on the products page");
        Assert.assertEquals(productsPage.getCartBadgeCounter(), 3, "Cart badge counter should still show 3 after returning to products page");
    }

    @Test
    public void shouldGoToCheckoutInfoPage_WhenCheckoutIsClicked() {
        // When
        CheckoutInfoPage checkoutInfoPage = cartPage.clickCheckoutButton();

        // Then
        Assert.assertTrue(checkoutInfoPage.isCheckoutInfoPageLoaded(), "Checkout info page is not displayed");
        Assert.assertEquals(checkoutInfoPage.getCheckoutInfoHeaderText(), "Checkout: Your Information", "Checkout info header text is incorrect");
        Assert.assertEquals(checkoutInfoPage.getCartBadgeCounter(), 3,
                "Cart badge counter should still show 3 after returning to products page");
    }

    @Test
    public void shouldUpdateCartBadgeCounterAndTotalItemsInCart_WhenItemIsRemovedFromCart() {
        // Given
        int totalItemsInCart = cartPage.getNumberOfItemsInCart();
        int totalBadgeCount = cartPage.getCartBadgeCounter();

        // When
        cartPage.clickRemoveButton(TestData.Products.BIKE_LIGHT);

        int totalItemsAfterRemove = cartPage.getNumberOfItemsInCart();
        int totalBadgeCountAfterRemove = cartPage.getCartBadgeCounter();

        // Then
        Assert.assertEquals(totalItemsInCart - 1, totalItemsAfterRemove, "Number of items in cart does not match after removing an item");
        Assert.assertEquals(totalBadgeCount - 1, totalBadgeCountAfterRemove, "Cart badge counter does not match after removing an item");
        Assert.assertFalse(cartPage.isItemDisplayed(TestData.Products.BIKE_LIGHT),
                "Bike Light should not be in cart after removal");
    }

    @Test
    public void shouldOpenItemPage_WhenItemIsClicked() {
        ProductDetailPage productDetailPage = cartPage.openProductDetailPage(TestData.Products.BIKE_LIGHT);

        Assert.assertTrue(productDetailPage.isProductPageLoaded(),
                "Item detail container is not displayed");

        Assert.assertEquals(productDetailPage.getProductName(), TestData.Products.BIKE_LIGHT,
                "Item name does not match");

        Assert.assertEquals(productDetailPage.getProductPrice(), TestData.Products.BIKE_LIGHT_PRICE,
                "Item price does not match");

        // verify the item is still in cart (Remove button visible on detail page too)
        Assert.assertTrue(productDetailPage.isRemoveButtonDisplayed(),
                "Remove button is not displayed on detail page");

    }



}
