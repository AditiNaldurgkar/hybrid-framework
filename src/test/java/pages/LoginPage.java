package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.DriverFactory;

public class LoginPage extends BasePage {

       public LoginPage(WebDriver driver) {
        super(driver);
    }
    public void login(String username, String password) {

        DriverFactory.getDriver().findElement(By.id("user-name")).sendKeys(username);
        DriverFactory.getDriver().findElement(By.id("password")).sendKeys(password);
        DriverFactory.getDriver().findElement(By.id("login-button")).click();
    }

    public String getTitle() {
        return DriverFactory.getDriver().getTitle();
    }
}