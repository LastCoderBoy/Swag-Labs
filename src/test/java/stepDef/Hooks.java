package stepDef;

import context.CucumberContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import pages.LoginPage;

public class Hooks {
    private final CucumberContext ctx;

    public Hooks(CucumberContext ctx) {
        this.ctx = ctx;
    }

    @Before
    public void setUp() {
        ctx.driver.get("https://www.saucedemo.com/");
        ctx.jsUtil.clearAllStorage();
        ctx.driver.navigate().refresh();
        ctx.loginPage = new LoginPage(ctx.driver, ctx.jsUtil);
    }

    @After
    public void tearDown() {
        if (ctx.driver != null) {
            ctx.driver.quit();
        }
    }
}
