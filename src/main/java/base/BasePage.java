package base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import util.JavaScriptUtil;
import util.WaitUtils;

import java.util.List;

public class BasePage {
    protected WebDriver driver;
    protected JavaScriptUtil jsUtil;

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

}
