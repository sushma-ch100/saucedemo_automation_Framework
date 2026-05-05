package com.qa.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setup() {
        System.out.println("Browser setup started...");

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-infobars");

        options.setExperimentalOption("prefs", Map.of(
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false,
                "profile.password_manager_leak_detection", false
        ));

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
    }

    public void takeScreenshot(String testName) {
        if (driver == null) {
            System.out.println("Driver is null. Screenshot not taken.");
            return;
        }

        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File dest = new File("artifacts/screenshots/" + testName + ".png");

        try {
            dest.getParentFile().mkdirs();
            FileHandler.copy(src, dest);
            System.out.println("Screenshot saved at: " + dest.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   @AfterMethod
public void tearDown(ITestResult result) throws InterruptedException {

    System.out.println("Test Status: " + result.getStatus());

    if (result.getStatus() == ITestResult.FAILURE) {
        System.out.println("Test failed. Taking screenshot...");
        takeScreenshot(result.getName());
    }

    Thread.sleep(2000);

    if (driver != null) {
        driver.quit();
    }
}
} 
