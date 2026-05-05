package com.qa.tests;

import com.qa.base.BaseTest;
import com.qa.pages.LoginPage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AddToCartTest extends BaseTest {

    @Test
    public void addToCartTest() {

        LoginPage login = new LoginPage(driver);
        login.login("standard_user", "secret_sauce"); 
        System.out.println("Add to cart test started...");

        // Add product to cart
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();

        // Verify cart count
        String cartCount = driver.findElement(By.className("shopping_cart_badge")).getText();
        Assert.assertEquals(cartCount, "1");
    }
} 
