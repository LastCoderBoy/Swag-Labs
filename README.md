# 🧪 SwagLabs Automation Testing Framework

A comprehensive test automation framework for **Sauce Labs (saucedemo.com)** built with modern best practices. This project demonstrates enterprise-grade automation using **Selenium**, **TestNG**, **Cucumber BDD**, and **Allure Reporting**.

---

## 📋 Table of Contents

- [Project Overview](#project-overview)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Setup & Installation](#setup--installation)
- [Running Tests](#running-tests)
- [Test Coverage](#test-coverage)
- [Architecture & Design Patterns](#architecture--design-patterns)
- [Best Practices Implemented](#best-practices-implemented)
- [Troubleshooting](#troubleshooting)
- [Contributing Guidelines](#contributing-guidelines)

---

## 🎯 Project Overview

SwagLabs is a **dual-approach test automation framework** supporting both:

1. **Unit Tests (TestNG)** - Direct test classes for specific functionalities
2. **BDD Tests (Cucumber)** - Behavior-Driven Development scenarios with Gherkin syntax

The framework automates critical user flows on **Sauce Labs demo application**:
- ✅ User Authentication (Login/Logout)
- ✅ Product Browsing & Filtering
- ✅ Shopping Cart Management
- ✅ Checkout Process (Info, Overview, Confirmation)

---

## 🛠 Tech Stack

| Component | Version | Purpose |
|-----------|---------|---------|
| **Java** | 22 | Programming language |
| **Selenium WebDriver** | 4.43.0 | Browser automation |
| **TestNG** | 7.11.0 | Unit testing framework |
| **Cucumber** | 7.34.3 | BDD test scenarios |
| **Allure Reports** | 2.39.0 | Test reporting & analytics |
| **Lombok** | 1.18.46 | Code generation (getters, builders, logs) |
| **SLF4J/Logback** | 2.0.18 / 1.5.6 | Logging framework |
| **Maven** | 3.5+ | Build & dependency management |

## 🔧 Prerequisites

Before setting up the project, ensure you have:

- **Java JDK 22+** - [Download](https://www.oracle.com/java/technologies/downloads/)
- **Maven 3.6+** - [Download](https://maven.apache.org/download.cgi)
- **Git** - Version control
- **IDE** (optional) - IntelliJ IDEA, Eclipse, or VS Code

### Verify Installation

```bash
java -version
mvn -version
git --version
```

---

## ⚙️ Setup & Installation

### Step 1: Clone the Repository

```bash
git clone https://github.com/LastCoderBoy/Swag-Labs.git
cd swagLabs
```

### Step 2: Install Dependencies

```bash
mvn clean install
```

This will:
- Download all project dependencies
- Compile Java source code
- Verify the build

### Step 3: Verify Setup

```bash
mvn compile
```

You should see:
```
[INFO] BUILD SUCCESS
```

---

## 🚀 Running Tests

### Run All Tests

```bash
mvn test
```

### Run Specific Test Class

```bash
# Unit tests only
mvn test -Dtest=LoginTest
mvn test -Dtest=ProductTests
mvn test -Dtest=CartTests
```

### Run Specific Test Method

```bash
mvn test -Dtest=ProductTests#shouldUpdateCartBadgeCounter_WhenProductsAreAddedAndRemoved
```

### Run BDD/Cucumber Tests

```bash
mvn test -Dtest=TestRunner
```

### Run Tests with Specific Tags (BDD)

```bash
# Run only smoke tests
mvn test -Dtest=TestRunner -Dcucumber.filter.tags="@smoke"

# Run regression tests
mvn test -Dtest=TestRunner -Dcucumber.filter.tags="@regression"
```

### Run Tests in Headless Mode (CI/CD)

```bash
mvn test -Dheadless=true
```

---

## 📊 Test Coverage

### Test Scenarios Covered

#### 🔐 **Authentication (Login)**
- ✅ Successful login with valid credentials
- ✅ Login failure with locked-out user
- ✅ Login failure with invalid credentials
- ✅ Error message validation

#### 🛍️ **Products**
- ✅ Products page header validation
- ✅ Product count verification
- ✅ Default sorting (A-Z)
- ✅ Sort by Name (A-Z)
- ✅ Sort by Name (Z-A)
- ✅ Sort by Price (Low to High)
- ✅ Sort by Price (High to Low)
- ✅ Add product to cart
- ✅ Remove product from cart
- ✅ Cart badge counter updates

#### 🛒 **Shopping Cart**
- ✅ Add multiple items to cart
- ✅ Remove items from cart
- ✅ Cart persistence across navigation
- ✅ Verify cart totals

#### 💳 **Checkout**
- ✅ Enter checkout information
- ✅ Verify checkout overview
- ✅ Complete order
- ✅ Order confirmation message

---

## 🏗️ Architecture & Design Patterns

### 1. **Page Object Model (POM)**

Each page/feature is encapsulated in a dedicated page class:

```java
public class LoginPage extends BasePage {
    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");
    
    public void enterUsername(String username) {
        set(usernameField, username);
    }
    
    public ProductsPage clickLoginButton() {
        click(loginButton);
        return new ProductsPage(driver);
    }
}
```

**Benefits:**
- ✅ Maintainability - Locators in one place
- ✅ Reusability - Common methods in BasePage
- ✅ Readability - Clear method names
- ✅ Scalability - Easy to add new pages

### 2. **BasePage Pattern**

All page objects inherit from `BasePage` which provides:

```java
public class BasePage {
    // Common waits and interactions
    protected WebElement find(By locator);
    protected List<WebElement> findAll(By locator);
    protected void set(By target, String value);
    protected void click(By locator);
    protected String getText(By locator);
}
```

### 3. **Explicit Waits**

Smart wait handling with configurable timeouts:

```java
public class WaitUtils {
    private static final int DEFAULT_TIMEOUT = 10;      // Default wait
    private static final int EXTENDED_TIMEOUT = 30;     // For slow elements
    private static final int SHORT_TIMEOUT = 5;         // Quick checks
    
    public static WebElement waitForVisible(WebDriver driver, By locator);
    public static void waitForUrlContains(WebDriver driver, String fragment);
    public static void waitForSpinnerToDisappear(WebDriver driver, By spinner);
}
```

### 4. **Cucumber BDD Integration**

Step definitions bridge Gherkin scenarios with page objects:

```gherkin
Feature: Login
  Scenario: Successful login
    Given the user is on the Swag Labs login page
    When the user enters username "standard_user"
    And the user enters password "secret_sauce"
    And the user clicks the login button
    Then the products page header should be displayed
```

Maps to:

```java
@Given("the user is on the Swag Labs login page")
public void userOnLoginPage() {
    loginPage.navigateToLoginPage();
}

@When("the user enters username {string}")
public void enterUsername(String username) {
    loginPage.enterUsername(username);
}
```

---

## ✨ Best Practices Implemented

### 1. **Instant Presence Checks**
```java
// ✅ GOOD: Use findElements() for instant check (0ms timeout)
List<WebElement> elements = driver.findElements(cartBadge);
if (elements.isEmpty()) {
    return 0; // No badge = cart is empty
}

// ❌ AVOID: This waits 10 seconds if element doesn't exist
find(cartBadge); // Times out after 10 seconds
```

### 2. **Comprehensive Storage Cleanup**
```java
public void clearAllStorage() {
    jsExecutor.executeScript("window.localStorage.clear();");
    jsExecutor.executeScript("window.sessionStorage.clear();");
    jsExecutor.executeScript("if (window.indexedDB) { ... }");
}
```

### 3. **Smart Wait Strategies**

**For spinner/loaders:**
```java
waitForSpinnerToDisappear(driver, spinnerLocator);
// Waits for element to appear, then disappear
```

**For visibility:**
```java
waitForVisible(driver, locator); // Explicit wait
```

### 4. **Lombok Integration**

Reduces boilerplate with annotations:

```java
@Getter
@Slf4j
@RequiredArgsConstructor
public class JavaScriptUtil {
    private final WebDriver driver;
    // Getters auto-generated, logging auto-configured
}
```

### 5. **Cross-Cutting Concerns**

TestListener captures screenshots on failure:
```java
@Override
public void onTestFailure(ITestResult result) {
    captureScreenshot(driver, result.getName());
    log.error("Test failed: {}", result.getName());
}
```

### 6. **Allure Reporting**

Automatic integration with Allure:
```java
@CucumberOptions(
    plugin = {
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
    }
)
```

### 7. **Type-Safe Enums**

Product sorting options:
```java
@Getter
@RequiredArgsConstructor
public enum SortOption {
    NAME_A_TO_Z("az", "Name (A to Z)"),
    PRICE_LOW_TO_HIGH("lohi", "Price (low to high)");
    
    private final String value;
    private final String displayName;
}
```

---

## 📈 Generating Reports

### Allure Report

Generate detailed HTML reports:

```bash
# Run tests with Allure plugin
mvn test

# Generate report
mvn allure:serve
```

This opens an interactive HTML report showing:
- ✅ Test execution timeline
- 📊 Test statistics
- 📸 Screenshots of failures
- 📝 Test logs
- 🏷️ Tags and categories

### Cucumber HTML Report

Generated automatically at:
```
target/cucumber-reports/cucumber-report.html
```

---

## 🐛 Troubleshooting

### Issue: Tests timeout after 10 seconds

**Cause:** Waiting for missing elements  
**Solution:** Use `driver.findElements()` for instant checks

```java
// ✅ Instant check
List<WebElement> items = driver.findElements(itemLocator);
if (items.isEmpty()) return;

// ❌ Slow - waits full timeout
find(itemLocator); // Throws exception after 10 seconds
```

### Issue: "jsUtil is null"

**Cause:** JavaScriptUtil not initialized  
**Solution:** Initialize in BasePage or BaseTest

```java
public BaseTest {
    protected JavaScriptUtil jsUtil;
    
    @BeforeClass
    public void setup() {
        jsUtil = new JavaScriptUtil(driver, (JavascriptExecutor) driver);
    }
}
```

### Issue: Cart state persists between tests

**Cause:** Browser storage not cleared  
**Solution:** Clear all storage mechanisms

```java
@BeforeMethod
public void cleanupStorage() {
    jsUtil.clearAllStorage(); // Clears localStorage, sessionStorage, indexedDB
    driver.navigate().refresh();
}
```

### Issue: Lombok not generating code

**Cause:** Annotation processor not configured  
**Solution:** Add maven-compiler-plugin with annotation processor path

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <configuration>
        <annotationProcessorPaths>
            <path>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
            </path>
        </annotationProcessorPaths>
    </configuration>
</plugin>
```

---

## 📝 Contributing Guidelines

### Branch Naming Convention
```
feature/description      # New feature
bugfix/description       # Bug fixes
refactor/description     # Code refactoring
```

### Test Naming Conventions
```java
// Unit tests - describe WHAT and WHEN
shouldDisplayRemoveButton_WhenAddToCartButtonIsClicked()
shouldSortLowToHighPrice_WhenPriceLowToHighFilterIsSelected()

// BDD scenarios - use business language
Scenario: User can add product to cart
Scenario: Cart badge updates when products are added
```

### Code Quality Standards

1. **Page Objects** - Locators as private final fields
2. **Methods** - Clear, descriptive names (verb + noun + condition)
3. **Logging** - Use SLF4J with @Slf4j annotation
4. **Error Handling** - Meaningful exception messages
5. **DRY Principle** - Extract common functionality to BasePage

### Pull Request Checklist

- ✅ Tests pass locally
- ✅ Code follows naming conventions
- ✅ No hardcoded waits (use WaitUtils)
- ✅ Page objects for new pages
- ✅ Documentation updated
- ✅ Screenshots/reports included

---

## 🔐 Security Notes

⚠️ **Never commit credentials** in test code:

```java
// ❌ DON'T
String password = "secret_sauce";

// ✅ DO - Use environment variables or external files
String password = System.getenv("TEST_PASSWORD");
```

---

## 📞 Support & Contact

- **Repository:** [LastCoderBoy/Swag-Labs](https://github.com/LastCoderBoy/Swag-Labs)
- **Website Under Test:** [saucedemo.com](https://www.saucedemo.com/)

---

## 📜 License

This project is open source and available under the MIT License.

**Happy Testing! 🚀**
