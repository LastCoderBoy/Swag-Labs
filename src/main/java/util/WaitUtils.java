package util;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class WaitUtils {

    private static final int DEFAULT_TIMEOUT  = 10;
    private static final int EXTENDED_TIMEOUT = 30;
    private static final int SHORT_TIMEOUT    = 5;
    private static final int POLL_INTERVAL    = 500; // ms

    private static WebDriverWait getWait(WebDriver driver, int seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds),
                Duration.ofMillis(POLL_INTERVAL));
    }

    // ─── Visibility ───────────────────────────────────────────────

    public static WebElement waitForVisible(WebDriver driver, By locator) {
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForVisible(WebDriver driver, By locator, int seconds) {
        return getWait(driver, seconds)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static List<WebElement> waitForVisibleAll(WebDriver driver, By locator) {
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    // ─── Clickability ─────────────────────────────────────────────

    public static WebElement waitForClickable(WebDriver driver, By locator) {
        return getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static WebElement waitForClickable(WebDriver driver, By locator, int seconds) {
        return getWait(driver, seconds)
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    // ─── Invisibility ─────────────────────────────────────────────

    public static void waitForInvisible(WebDriver driver, By locator) {
        getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    // ─── Page / URL ───────────────────────────────────────────────

    public static void waitForUrlContains(WebDriver driver, String fragment) {
        getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.urlContains(fragment));
    }

    public static void waitForPageTitle(WebDriver driver, String title) {
        getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.titleContains(title));
    }

    // ─── Text ─────────────────────────────────────────────────────

    public static void waitForTextPresent(WebDriver driver, By locator, String text) {
        getWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    // ─── Alert ────────────────────────────────────────────────────

    public static Alert waitForAlert(WebDriver driver) {
        return getWait(driver, SHORT_TIMEOUT)
                .until(ExpectedConditions.alertIsPresent());
    }

    // ─── Loading spinners ──────────────────────────────────────────

    public static void waitForSpinnerToDisappear(WebDriver driver, By spinnerLocator) {
        try {
            // First wait for it to appear (it may not always show)
            getWait(driver, SHORT_TIMEOUT)
                    .until(ExpectedConditions.visibilityOfElementLocated(spinnerLocator));
        } catch (TimeoutException ignored) {
            // Spinner never appeared - page was already ready
        }
        // Then wait for it to disappear
        getWait(driver, EXTENDED_TIMEOUT)
                .until(ExpectedConditions.invisibilityOfElementLocated(spinnerLocator));
    }
}
