package stepdefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import pages.LoginPage;
import pages.BasePage;
import utils.DriverFactory;

import java.time.Duration;
import java.util.List;

public class SauceSteps {

    WebDriver driver;
    LoginPage loginPage;
    BasePage basePage;
    WebDriverWait wait;

    boolean loginSuccess;
    @Given("user logs in with {string} and {string}")
public void login(String username, String password) {

    driver = DriverFactory.getDriver();
    loginPage = new LoginPage(driver);
    basePage = new BasePage(driver);

    loginSuccess = true;
    wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    loginPage.login(username, password);
    basePage.handleRandomPopup();

    try {
        // 🔥 WAIT FOR PAGE CHANGE (MOST RELIABLE)
        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("inventory"),
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3[data-test='error']"))
        ));
    } catch (Exception e) {
        System.out.println("⚠️ Login state unclear");
    }

    // 🔥 FINAL DECISION USING URL (IMPORTANT)
    if (!driver.getCurrentUrl().contains("inventory")) {
        System.out.println("❌ Login failed for: " + username);
        loginSuccess = false;
        return;
    }

    System.out.println("✅ Login success for: " + username);
}

    @When("user applies low to high price filter")
    public void filter() {
        if (!loginSuccess) return;

        WebElement dropdown = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.className("product_sort_container")
                )
        );

        new Select(dropdown).selectByValue("lohi");
    }

    @When("user adds first product to cart")
    public void add() {

        if (!loginSuccess) return;

        List<WebElement> addButtons = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(
                        By.xpath("//button[contains(text(),'Add to cart')]")
                )
        );

        addButtons.get(0).click();
    }

    @When("user navigates to cart")
    public void cart() {

        if (!loginSuccess) return;

        WebElement cartIcon = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.className("shopping_cart_link")
                )
        );

        cartIcon.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".cart_list")
        ));
    }

    @When("user proceeds to checkout")
    public void checkout() {

        if (!loginSuccess) return;

        WebElement checkoutBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("[data-test='checkout']")
                )
        );

        checkoutBtn.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name")))
                .sendKeys("Aditi");

        driver.findElement(By.id("last-name")).sendKeys("Test");
        driver.findElement(By.id("postal-code")).sendKeys("12345");

        driver.findElement(By.id("continue")).click();
    }

    @Then("user should reach checkout page")
public void validate() {
    if (!loginSuccess) {
        Assert.fail("Login failed — checkout step should not be reached.");
        return;
    }

    wait.until(ExpectedConditions.urlContains("checkout-step-two"));
    Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-two"),
            "Checkout failed!");
    System.out.println("🎉 Checkout successful");
}
@Then("user should see login error")
public void validateLoginError() {
    Assert.assertFalse(loginSuccess, "Expected login to fail but it succeeded!");

    WebElement error = wait.until(
        ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("h3[data-test='error']")
        )
    );

    Assert.assertTrue(error.isDisplayed(), "Error message not shown!");
    System.out.println("✅ Login correctly failed with: " + error.getText());
}
}