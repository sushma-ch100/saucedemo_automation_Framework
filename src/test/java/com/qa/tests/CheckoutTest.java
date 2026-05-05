package com.qa.tests;

import com.qa.base.BaseTest;
import com.qa.pages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class CheckoutTest extends BaseTest {

    @Test
    public void checkoutFlowTest() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        LoginPage login = new LoginPage(driver);
        login.login("standard_user", "secret_sauce");

        wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-sauce-labs-backpack"))).click();

        // Stable direct navigation to checkout information page
        driver.navigate().to("https://www.saucedemo.com/checkout-step-one.html");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name"))).sendKeys("Sushma");
        driver.findElement(By.id("last-name")).sendKeys("Test");
        driver.findElement(By.id("postal-code")).sendKeys("12345");

        driver.findElement(By.id("continue")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("finish"))).click();

        String confirmationMessage = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("complete-header")))
                .getText();

        Assert.assertTrue(confirmationMessage.contains("Thank you"), "Checkout was not successful");
    }
}
