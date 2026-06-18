package stepDef;

import context.CucumberContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class LoginSteps {

    private final CucumberContext ctx;

    public LoginSteps(CucumberContext ctx) {
        this.ctx = ctx;
    }

    @Given("the user is on the Swag Labs login page")
    public void theUserIsOnTheLoginPage() {
        // Hooks @Before already navigates to the URL
        // this step just documents the precondition in the report
    }

    @When("the user enters username {string}")
    public void theUserEntersUsername(String username) {
        ctx.loginPage.enterUsername(username);
    }

    @And("the user enters password {string}")
    public void theUserEntersPassword(String password) {
        ctx.loginPage.enterPassword(password);
    }

    @And("the user clicks the login button")
    public void theUserClicksTheLoginButton() {
        ctx.productsPage = ctx.loginPage.clickLoginButton();
    }

    @Then("the products page header should be displayed")
    public void theProductsPageHeaderShouldBeDisplayed() {
        Assert.assertTrue(ctx.productsPage.isProductsHeaderDisplayed(),
                "Products page header is not displayed");
    }

    @Then("the error message should be {string}")
    public void theErrorMessageShouldBe(String expectedMessage) {
        Assert.assertEquals(ctx.loginPage.getErrorMessage(), expectedMessage,
                "Error message does not match");
    }
}