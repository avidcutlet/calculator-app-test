package com.example.calculator.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

/**
 * The class is a utility for capturing and saving
 * screenshots during test execution.
 */
public class ScreenshotUtil {
	private WebDriver driver;
	
	public ScreenshotUtil(WebDriver driver) {
		this.driver = driver;
	}
	
    /**
     * Captures a screenshot of the current browser window
     * @param fileNamePrefix The prefix to include in the screenshot filename
     */
	public void takeScreenshot(String fileNamePrefix) {
		try {
			File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			// Generate a timestamp for a unique filename
			String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
			String filePath = "reports/screenshots/"+ fileNamePrefix + "_" + timestamp + ".png";
			FileHandler.copy(src, new File(filePath));
			System.out.println("[INFO] Screenshot saved: " + filePath);
			
		} catch (IOException io) {
			System.out.println("[ERROR] Failed to capture screenshot: " + io.getMessage());
		}
	}
}
