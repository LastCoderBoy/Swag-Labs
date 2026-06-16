package base;

import listener.TestListener;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import pages.LoginPage;

import java.util.Map;

@Getter
@Listeners(TestListener.class)
public class BaseTest {
    protected BasePage basePage;
    protected LoginPage loginPage; // Adding this instance, as every test will go through this page.
    protected WebDriver driver;
    private final String SWAGLABS_URL = "https://www.saucedemo.com/";

    @BeforeClass
    public void setup(){
        ChromeOptions options = new ChromeOptions();
        // ── Headless (CI) ────────────────────────────────────────────
        if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
            options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
        }

        // ── Suppress Chrome password manager popups ──────────────────
        options.addArguments("--disable-save-password-bubble");
        options.setExperimentalOption("prefs", Map.of(
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false,
                "profile.password_manager_leak_detection", false  // ← this one kills the "Change Your Password" alert
        ));

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @BeforeMethod
    public void loadApplication(){
        driver.get(SWAGLABS_URL);
        basePage = new BasePage(driver);
        loginPage = new LoginPage(driver);
    }

    @AfterClass
    public void tearDown() throws InterruptedException {
        Thread.sleep(3000);
        driver.quit();
    }

}
