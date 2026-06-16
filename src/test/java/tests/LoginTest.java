package tests;

import base.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ProductsPage;

@Slf4j
public class LoginTest extends BaseTest {
    private final String STANDARD_USERNAME = "standard_user";
    private final String LOCKED_USERNAME = "locked_out_user";
    private final String PASSWORD = "secret_sauce";

    @Test
    public void shouldLoginSuccessfully_WhenValidCredentialsProvided(){
        // Given & When
        loginPage.enterUsername(STANDARD_USERNAME);
        loginPage.enterPassword(PASSWORD);
        ProductsPage productsPage = loginPage.clickLoginButton();

        // Then
        Assert.assertTrue(productsPage.isProductsHeaderDisplayed());
    }

    @Test
    public void shouldGetLockedErrorMsg_WhenLockedCredentialsProvided(){
        // Given & When
        loginPage.enterUsername(LOCKED_USERNAME);
        loginPage.enterPassword(PASSWORD);
        loginPage.clickLoginButton();

        String actualErrorMsg = loginPage.getErrorMessage();

        // Then
        Assert.assertEquals(actualErrorMsg, "Epic sadface: Sorry, this user has been locked out.");
    }

    @Test
    public void shouldGetErrorMsg_WhenInvalidCredentialsProvided(){
        // Given & When
        loginPage.enterUsername("invalid_user");
        loginPage.enterPassword("invalid_pass");
        loginPage.clickLoginButton();

        String actualErrorMsg = loginPage.getErrorMessage();

        // Then
        Assert.assertEquals(actualErrorMsg, "Epic sadface: Username and password do not match any user in this service");
    }
}
