package tests;

import base.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ProductsPage;
import testdata.TestData;

@Slf4j
public class LoginTest extends BaseTest {

    @Test
    public void shouldLoginSuccessfully_WhenValidCredentialsProvided(){
        // Given & When
        loginPage.enterUsername(TestData.Credentials.STANDARD_USER);
        loginPage.enterPassword(TestData.Credentials.PASSWORD);
        ProductsPage productsPage = loginPage.clickLoginButton();

        // Then
        Assert.assertTrue(productsPage.isProductsHeaderDisplayed());
    }

    @Test
    public void shouldGetLockedErrorMsg_WhenLockedCredentialsProvided(){
        // Given & When
        loginPage.enterUsername(TestData.Credentials.LOCKED_OUT_USER);
        loginPage.enterPassword(TestData.Credentials.PASSWORD);
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
