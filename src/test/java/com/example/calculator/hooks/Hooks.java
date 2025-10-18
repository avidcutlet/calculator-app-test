package com.example.calculator.hooks;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

import com.example.calculator.factory.WebDriverFactory;
import com.example.calculator.utils.AllureUtil;
import com.example.calculator.utils.ConfigReader;
import com.example.calculator.utils.LoggerUtil;
import com.example.calculator_manager.DriverManager;
import com.google.common.collect.ImmutableMap;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {
	
	public static WebDriver driver;
	private AllureUtil allureUtil;
	private static final Logger logger = LoggerUtil.getLogger(Hooks.class);
	
	@Before
	public void setUp(Scenario scenario) {
		
//		String env = System.getProperty("env", "dev");
//		ConfigReader.loadProperties(env);
//
//		String browser = System.getProperty("browser", ConfigReader.get("BROWSER"));
//		String url = ConfigReader.get("APP_URL");
//        boolean headless = Boolean.parseBoolean(System.getProperty("headless", ConfigReader.get("HEADLESS")));
//
//		driver = WebDriverFactory.loadDriver(browser, headless);
//		
//        if (headless) {
//            // set an explicit size so screenshots aren’t tiny/cropped
//            driver.manage().window().setSize(new Dimension(1920, 1080));
//        } else {
//            driver.manage().window().maximize();
//        }
//        
//		driver.get(url);
//		DriverManager.setDriver(driver);
//		
//		allureUtil = new AllureUtil(driver);
//		
//		allureUtil.writeAllureEnvironment(
//				ImmutableMap.<String, String>builder()
//					.put("OS", System.getProperty("os.name"))
//					.put("Browser", browser)
//	                .put("Headless", String.valueOf(headless))
//					.put("Environment", env)
//					.build()
//		);
//		
//		logger.info("Starting scenario: " + scenario.getName());
		
        // 1) Load env file (default: dev)
        String env = System.getProperty("env", System.getenv().getOrDefault("ENV", "dev"));
        ConfigReader.loadProperties(env);

        // 2) Resolve settings with precedence: -D -> ENV -> config file
        String browser = System.getProperty("browser",
                System.getenv().getOrDefault("BROWSER", ConfigReader.get("BROWSER")));

        boolean headless = Boolean.parseBoolean(
                System.getProperty("headless",
                        System.getenv().getOrDefault("HEADLESS", ConfigReader.get("HEADLESS"))));

        // IMPORTANT: allow CI override for base URL
        String url = System.getProperty("baseUrl",
                System.getenv().getOrDefault("BASE_URL", ConfigReader.get("APP_URL")));

        if (url == null || url.isBlank()) {
            throw new IllegalStateException("Base URL is not configured. Provide -DbaseUrl=... or set APP_URL.");
        }

        // 3) Start driver
        driver = WebDriverFactory.loadDriver(browser, headless);
        
        if (headless) {
            driver.manage().window().setSize(new Dimension(1920, 1080));
        } else {
            driver.manage().window().maximize();
        }

        // 4) Navigate & stash driver
        driver.get(url);
        DriverManager.setDriver(driver);

        // 5) Allure env + logs
        allureUtil = new AllureUtil(driver);
        allureUtil.writeAllureEnvironment(
            ImmutableMap.<String, String>builder()
                .put("OS", System.getProperty("os.name"))
                .put("Browser", browser)
                .put("Headless", String.valueOf(headless))
                .put("Environment", env)
                .put("BaseUrl", url)
                .build()
        );

        logger.info("Starting scenario: {}", scenario.getName());
        logger.info("Config → env={}, browser={}, headless={}, baseUrl={}", env, browser, headless, url);
	}
	
	@After(order=0)
	public void tearDown() {
		// Quit the WebDriver instance
		if (driver != null) {
			driver.quit();
		}
		
		logger.info("Closing the browser.");
	}
	
	@After(order=1)
	public void captureFailure(Scenario scenario) {
//		if (scenario.isFailed()) {
//			allureUtil.captureAndAttachScreenshot();
//		}
		
        if (scenario.isFailed() && allureUtil != null) {
            allureUtil.captureAndAttachScreenshot();
        }
	}
	
	@AfterStep
	public void afterEachStep(Scenario scenario) {
//		allureUtil.captureAndAttachScreenshot();
		
        // Toggle heavy step-by-step screenshots via -DscreenshotEveryStep=true
        boolean everyStep = Boolean.parseBoolean(System.getProperty(
                "screenshotEveryStep",
                System.getenv().getOrDefault("SCREENSHOT_EVERY_STEP", "false")));
        if (everyStep && allureUtil != null) {
            allureUtil.captureAndAttachScreenshot();
        }
	}

}
