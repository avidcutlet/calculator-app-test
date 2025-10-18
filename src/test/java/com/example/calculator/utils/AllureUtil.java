package com.example.calculator.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.example.calculator.listener.StepListener;

import io.qameta.allure.Allure;

public class AllureUtil {
	
	private WebDriver driver;
	private static final Logger logger = LoggerUtil.getLogger(ElementActionUtils.class);
	
	public AllureUtil(WebDriver driver) {
		this.driver = driver;
	}
	
	/**
	 * Attaches a text message to the Allure report.
	 *
	 * @param message The text to attach
	 */
	public void attachText(String message) {
	    // Update the current step name in Allure report
	    Allure.getLifecycle().updateStep(s -> s.setName("📄 " + StepListener.currentStep));
	    // Attach the text as a .txt file
	    Allure.addAttachment("Sample Text", "text/plain", new ByteArrayInputStream(
	            message.getBytes(StandardCharsets.UTF_8)), ".txt");
	}

	/**
	 * Captures a screenshot and attaches it to the Allure report.
	 */
	public void captureAndAttachScreenshot() {
	    byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	    Allure.getLifecycle().updateStep(s -> s.setName("📸 " + StepListener.currentStep)); // override name
	    // Attach the screenshot as a .png file
	    Allure.addAttachment("Screenshot", "image/png", new ByteArrayInputStream(screenshotBytes), ".png");
	}

	/**
	 * Writes environment properties to Allure's environment.properties file.
	 *
	 * @param env Map of environment properties
	 */
	public void writeAllureEnvironment(Map<String, String> env) {
	    // Create the environment.properties file in allure-results directory
	    File envFile = new File("target/allure-results/environment.properties");
	    // Ensure parent directories exist
	    envFile.getParentFile().mkdirs();
	    try (FileWriter writer = new FileWriter(envFile)) {
	        // Write each property as key=value
	        for (Map.Entry<String, String> entry : env.entrySet()) {
	            writer.write(entry.getKey() + "=" + entry.getValue() + "\n");
	        }
	    } catch (IOException e) {
	        logger.error("Failed to write Allure environment file", e);
	    }
	}
}
