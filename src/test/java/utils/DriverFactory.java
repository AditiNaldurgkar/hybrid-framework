package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private static ChromeOptions getChromeOptions() {
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--password-store=basic");
        options.addArguments("--disable-save-password-bubble");

        return options;
    }

    private static EdgeOptions getEdgeOptions() {
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);

        EdgeOptions options = new EdgeOptions();
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--password-store=basic");
        options.addArguments("--disable-save-password-bubble");

        return options;
    }

    public static WebDriver initDriver(String browser) {

        try {
            if (browser.equalsIgnoreCase("chrome")) {

                ChromeOptions chromeOptions = getChromeOptions();

                try {
                    driver.set(new RemoteWebDriver(
                            new URL("http://localhost:4444"),
                            chromeOptions   // ✅ pass options to grid too
                    ));
                } catch (Exception e) {
                    System.out.println("⚠️ Grid unavailable, falling back to local ChromeDriver");
                    WebDriverManager.chromedriver().setup();
                    driver.set(new ChromeDriver(chromeOptions));  // ✅ pass options locally too
                }

            } else {

                EdgeOptions edgeOptions = getEdgeOptions();

                try {
                    driver.set(new RemoteWebDriver(
                            new URL("http://localhost:4444"),
                            edgeOptions    // ✅ pass options to grid too
                    ));
                } catch (Exception e) {
                    System.out.println("⚠️ Grid unavailable, falling back to local EdgeDriver");
                    WebDriverManager.edgedriver().setup();
                    driver.set(new EdgeDriver(edgeOptions));   // ✅ pass options locally too
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Driver init failed", e);
        }

        return getDriver();
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}