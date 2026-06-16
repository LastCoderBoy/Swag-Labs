package util;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@RequiredArgsConstructor
public class JavaScriptUtil {
    private final WebDriver driver;
    private final JavascriptExecutor jsExecutor;

    public void scrollToElementJS(By locator) {
        WebElement element = WaitUtils.waitForVisible(driver, locator);
        String scrollScript = "arguments[0].scrollIntoView({block:'end'});";
        jsExecutor.executeScript(scrollScript, element);
    }

    public void clickJS(By locator) {
        WebElement element = WaitUtils.waitForClickable(driver, locator);
        String clickScript = "arguments[0].click();";
        jsExecutor.executeScript(clickScript, element);
    }

}
