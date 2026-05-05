package com.qa.tests;

import com.qa.base.BaseTest;
import com.qa.pages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class EmptyCartCheckoutTest extends BaseTest {

    @Test
    public void emptyCartCheckoutTest() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        LoginPage login = new LoginPage(driver);
        login.login("standard_user", "secret_sauce");

        driver.navigate().to("https://www.saucedemo.com/cart.html");

        wait.until(ExpectedConditions.elementToBeClickable(By.id("checkout"))).click();

        String currentUrl = driver.getCurrentUrl();

        Assert.assertFalse(
                currentUrl.contains("checkout-step-one"),
                "BUG: User is allowed to proceed to checkout with an empty cart"
        );
    }
}