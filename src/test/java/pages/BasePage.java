package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

  public void handleRandomPopup() {
    try {
        // This popup is a web-rendered modal (not native Chrome UI)
        // Only works if popup is in DOM — use ChromeOptions fix as primary
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement okBtn = shortWait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//button[normalize-space()='OK']")
            )
        );
        okBtn.click();
        System.out.println("✅ Popup closed");
    } catch (TimeoutException e) {
        System.out.println("ℹ️ No popup — skipping");
    }
}
}