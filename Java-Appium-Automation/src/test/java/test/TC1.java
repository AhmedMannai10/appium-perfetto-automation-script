package test;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TC1 {

    private AndroidDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName("samsung SM-A225F")
                .setPlatformName("Android")
                .setPlatformVersion("13")
                .setAutomationName("UiAutomator2")
                .setEnsureWebviewsHavePages(true)
                .setNativeWebScreenshot(true)
                .setNewCommandTimeout(Duration.ofSeconds(3600));

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void sampleTest() {
        //driver.executeScript("mobile: pressKey", Map.of("keycode", 4));
        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.accessibilityId("Baseline Profiles Codelab"))).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.androidUIAutomator("new UiSelector().text(\"Organic\")"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.androidUIAutomator("new UiSelector().text(\"Dairy-free\")"))).click();

        String scrollCommand = "new UiScrollable(new UiSelector().scrollable(true)).scrollForward()";
        driver.findElement(AppiumBy.androidUIAutomator(scrollCommand));



        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.view.View\").instance(18)"))).click();
        var el5 = wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.view.View\").instance(6)")));
        el5.click();
        el5.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.Button\").instance(2)"))).click();
        var el7 = wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.Button\").instance(1)")));
        el7.click();
        el7.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.accessibilityId("MY CART"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.Button\").instance(3)"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.accessibilityId("PROFILE"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.accessibilityId("HOME"))).click();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}