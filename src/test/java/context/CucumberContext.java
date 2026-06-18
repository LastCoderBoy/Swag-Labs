package context;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.LoginPage;
import pages.ProductsPage;
import util.JavaScriptUtil;

import java.util.Map;

public class CucumberContext {
    public WebDriver driver;
    public JavaScriptUtil jsUtil;
    public LoginPage loginPage;
    public ProductsPage productsPage;

    public CucumberContext() {
        ChromeOptions options = new ChromeOptions();
        if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
            options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
        }
        options.addArguments("--disable-save-password-bubble");
        options.setExperimentalOption("prefs", Map.of(
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false,
                "profile.password_manager_leak_detection", false
        ));

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        jsUtil = new JavaScriptUtil(driver);
        loginPage = new LoginPage(driver, jsUtil);
    }
}
