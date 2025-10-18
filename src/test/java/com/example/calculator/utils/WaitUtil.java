package com.example.calculator.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtil {

    private final WebDriver driver;

    public WaitUtil(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement waitForElementVisible(By locator, long timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForElementClickable(By locator, long timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public boolean waitForTextPresent(By locator, String text, long timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    public void waitForUrlContains(String partialUrl, long timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.urlContains(partialUrl));
    }

    public WebElement fluentWait(final By locator, long timeout, long pollingTime) {
        Wait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofMillis(pollingTime))
                .ignoring(NoSuchElementException.class);
        return fluentWait.until(d -> d.findElement(locator));
    }
    
    public boolean waitForUrlToBe(String url, long timeout) {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(d -> {
            String currentUrl = d.getCurrentUrl();
            return currentUrl.replaceAll("/$", "").equals(url.replaceAll("/$", "")); // remove trailing slash before comparing
        });
    }
}
