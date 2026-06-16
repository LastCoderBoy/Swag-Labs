package util;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class JavaScriptUtil {
    private final WebDriver driver;
    private final JavascriptExecutor jsExecutor;

    public JavaScriptUtil(WebDriver driver) {
        this.driver = driver;
        this.jsExecutor = (JavascriptExecutor) driver;
    }

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

    public void clearAllStorage() {
        // Clear localStorage
        jsExecutor.executeScript("window.localStorage.clear();");
        // Clear sessionStorage
        jsExecutor.executeScript("window.sessionStorage.clear();");
        // Clear IndexedDB (fallback for some apps)
        jsExecutor.executeScript(
            "if (window.indexedDB) { " +
            "  window.indexedDB.databases && window.indexedDB.databases().then(databases => { " +
            "    databases.forEach(db => { window.indexedDB.deleteDatabase(db.name); }); " +
            "  }); " +
            "}"
        );
    }

    public void clearLocalStorage() {
        jsExecutor.executeScript("window.localStorage.clear();");
    }
}
