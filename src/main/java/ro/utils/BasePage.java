package ro.utils;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.asserts.SoftAssert;

import java.net.URL;

public class BasePage {

    private static final Logger LOG = LoggerFactory.getLogger(BasePage.class);
    public static EnhancedDriver driver;
    public static SoftAssert softAssert = new SoftAssert();
    private static URL gridURL;
    private static Platform platform;
    private static String browser;
    private static String browserVersion;

    public BasePage() {
    }

    public BasePage(URL gridURL, Platform platform, String browser, String browserVersion) {
        this.gridURL = gridURL;
        this.platform = platform;
        this.browser = browser;
        this.browserVersion = browserVersion;
        driver = getNewDriver();
    }

    public static void setNewDriver() {
        driver = getNewDriver();
    }

    public static EnhancedDriver getDriver() {
        return driver;
    }

    public static EnhancedDriver getNewDriver() {
        EnhancedDriver driver = new EnhancedDriver(gridURL, new DesiredCapabilities(browser, browserVersion, platform));
        driver.manage().window().maximize();
        return driver;
    }

    public static void navigateTo(String url) {
        driver.get(url);
    }
}