package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utils.DriverFactory;

public class hooks {

    @Before
    public void setUp() {

        String browser = System.getProperty("browser", "chrome");

        DriverFactory.initDriver(browser);
        DriverFactory.getDriver().get("https://www.saucedemo.com/");
    }

    @After
    public void tearDown(Scenario scenario) {

        if (scenario.isFailed()) {
            byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver())
                    .getScreenshotAs(OutputType.BYTES);

            scenario.attach(screenshot, "image/png", "Failure Screenshot");
        }

        DriverFactory.quitDriver();
    }
}