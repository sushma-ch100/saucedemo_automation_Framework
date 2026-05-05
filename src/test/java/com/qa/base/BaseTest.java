package com.qa.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.Map;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setup() {
        System.out.println("Browser setup started...");

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
 
        options.addArguments("--user-data-dir=/tmp/chrome-test-profile");
        options.addArguments("--incognito");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-infobars");

        options.setExperimentalOption("prefs", Map.of(
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false
        ));

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
    }

    @AfterMethod
    public void tearDown() throws InterruptedException {
        Thread.sleep(3000);

        if (driver != null) {
            driver.quit();
        }
    }
} 
