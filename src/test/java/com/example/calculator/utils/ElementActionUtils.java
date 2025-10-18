package com.example.calculator.utils;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * The class provides reusable utility methods for interacting with web elements
 * It simplifies common element actions such as clicking, entering text,
 * verifying visibility, and comparing text values. Each action includes:
 */
public class ElementActionUtils {
	
	private WebDriver driver;
	private WaitUtil waitUtil;
	private static final Logger logger = LoggerUtil.getLogger(ElementActionUtils.class);
	
	// Constant for default wait time (in seconds)
	private final long visibilityTimeout   = Long.parseLong(ConfigReader.get("VISIBILITY_TIMEOUT"));
	private final long clickabilityTimeout = Long.parseLong(ConfigReader.get("CLICKABLE_TIMEOUT"));
	private final long fluentTimeout       = Long.parseLong(ConfigReader.get("FLUENT_TIMEOUT"));
	private final long pollingTime         = Long.parseLong(ConfigReader.get("POLLING_INTERVAL"));
	
	public ElementActionUtils(WebDriver driver) {
		this.driver = driver;
		this.waitUtil = new WaitUtil(driver);
	}
	
	public WebElement findElement(By locator) {
        try {
            WebElement element = waitUtil.waitForElementVisible(locator, visibilityTimeout);
            return element;
        } catch (Exception e) {
			logger.error("Failed to find on element: " + locator.toString());
			throw e;
        }
	}
	
    /**
     * Waits for an element to be clickable and clicks it.
     * Captures a screenshot for success and failure cases.
     *
     * @param locator The {@link By} locator of the element to be clicked.
     */
	public void clickElement(By locator) {
		try {
			WebElement element = waitUtil.waitForElementClickable(locator, clickabilityTimeout);
			highlightElement(driver, element);
			element.click();
			logger.info("Clicked on element: " + locator.toString());		
		} catch (Exception e ) {
			logger.error("Failed to click on element: " + locator.toString());
			throw e;
		}
	}
	
	public void clickElementByFluentWait(By locator) {
		try {
			WebElement element = waitUtil.fluentWait(locator, fluentTimeout, pollingTime);
			highlightElement(driver, element);
			element.click();
			logger.info("Clicked on element (using FluentWait): " + locator.toString());
		} catch (Exception e ) {
			logger.error("Failed to click on element (using FluentWait): " + locator.toString());
			throw e;
		}
	}
	
    /**
     * Waits for an element to be visible and enters text into it.
     * Captures a screenshot for success and failure cases.
     *
     * @param locator The By locator of the input field.
     * @param text The text to be entered into the element.
     */
	public void inputElement(By locator, String text) {
		try {
			WebElement element = waitUtil.waitForElementVisible(locator, visibilityTimeout);
			highlightElement(driver, element);
			element.clear();
			element.sendKeys(text);
			logger.info("Entered text in element: " + locator.toString() + " - Text: " + text);
		} catch (Exception e) {
			logger.error("Failed to enter text in element: " + locator.toString() + " - Text: " + text);
			throw e;
		}
	}
	
	public void selectDropdown(By locator, String text) {
		try {
			WebElement element = waitUtil.waitForElementVisible(locator, visibilityTimeout);
			highlightElement(driver, element);
			
		    Select select = new Select(element);
		    select.selectByValue(text);
			logger.info("Select in the dropdown: " + locator.toString() + " - Text: " + text);
		} catch (Exception e) {
			logger.error("Failed to enter text in element: " + locator.toString() + " - Text: " + text);
			throw e;
		}
	}
	
    /**
     * Waits for an element to be visible and verifies that it is displayed.
     * Captures a screenshot for success and failure cases.
     *
     * @param locator The By locator of the element to verify.
     */
	public void verifyDisplayed(By locator) {
		try {
			WebElement element = waitUtil.waitForElementVisible(locator, visibilityTimeout);
			highlightElement(driver, element);
			element.isDisplayed();
			logger.info("Element is displayed: " + locator.toString());
		} catch (Exception e) {
			logger.error("Element is not displayed: " + locator.toString());
			throw e;
		}
	}
	
    public void verifyTextContains(String actual, String expectedPartial) {
        if (actual != null && actual.contains(expectedPartial)) {
        	logger.info("Text contains: " + expectedPartial);
        } else {
        	logger.error("Expected to contain: " + expectedPartial + ", but was: " + actual);
        }
    }
	
    /**
     * Retrieves the text of an element and compares it with the expected text.
     * Captures a screenshot for success and failure cases.
     *
     * @param locator The By locator of the element containing text.
     * @param expectedText The expected text to compare with.
     * @throws AssertionError if the actual text does not match the expected text.
     */
	public void getTextAndCompare(By locator, String expectedText) {
		try {
			WebElement element = waitUtil.waitForElementVisible(locator, visibilityTimeout);
			highlightElement(driver, element);
			
			// compare expected and actual text
			if (element.getText().equals(expectedText)) {
				logger.info("Text matches expected: " + expectedText);
			} else {
				logger.info("Text does not match expected. Found: " + element.getText() + ", Expected: " + expectedText);
				throw new AssertionError("Text does not match expected.");
			}
		} catch (Exception e) {
			logger.error("Failed to get text or compare: " + locator.toString());
			throw e;
		}
	}
	
    /**
     * Retrieves the value of a specified attribute from the element identified by the locator.
     * 
     * @param driver the WebDriver instance
     * @param locator the By locator of the element
     * @param attribute the name of the attribute to retrieve
     * @return the attribute value, or null if unable to retrieve
     */
    public void getTextByAttributeAndCompare(By locator, String attribute, String expectedText) {
        try {
            WebElement element = waitUtil.waitForElementVisible(locator, visibilityTimeout);
            String attributeText = element.getDomAttribute(attribute);
            
            // compare expected and actual text
 			if (attributeText != null && attributeText.equals(expectedText)) {
 				logger.info("Text matches expected: " + expectedText);
 			} else {
 				logger.info("Text does not match expected. Found: " + attributeText + ", Expected: " + expectedText);
 				throw new AssertionError("Text does not match expected.");
 			}
        } catch (Exception e) {
        	logger.error("Unable to get attribute '" + attribute + "' from element: " + locator.toString() + ". Reason: " + e.getMessage());
        	throw e;
        }
    }
	
    /**
     * Highlights the given element by applying a blue border using JavaScript.
     * Helpful for debugging during test execution.
     *
     * @param driver the WebDriver instance
     * @param locator the WebElement to be highlighted
     */
    private static void highlightElement(WebDriver driver, WebElement locator) {
        try {
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
            		"arguments[0].style.border='2px solid yellow'; arguments[0].style.backgroundColor='yellow';", locator);
        } catch (Exception e) {
			logger.error("Failed to get text or compare: " + locator.toString());
			throw e;
        }
    }
	
}
