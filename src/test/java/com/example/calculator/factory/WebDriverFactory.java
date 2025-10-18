package com.example.calculator.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class WebDriverFactory {
	public static WebDriver loadDriver(String browser, boolean headless) {
		switch (browser.toLowerCase()) {
			case "chrome": {
                ChromeOptions opts = new ChromeOptions();
                
                if (headless) {
                    opts.addArguments("--headless=new");
                    opts.addArguments("--window-size=1920,1080");
                    opts.addArguments("--disable-gpu");
                    opts.addArguments("--no-sandbox");
                }
                
				return new ChromeDriver(opts);
			}
			case "firefox": {
                FirefoxOptions opts = new FirefoxOptions();
                
                if (headless) {
                    opts.addArguments("-headless");
                    opts.addArguments("--width=1920");
                    opts.addArguments("--height=1080");
                }
                
                return new FirefoxDriver(opts);
			}
			case "edge": {
                EdgeOptions opts = new EdgeOptions();
                
                if (headless) {
                    opts.addArguments("--headless=new");
                    opts.addArguments("--window-size=1920,1080");
                }
                
                return new EdgeDriver(opts);
			}
			default:
				throw new IllegalArgumentException("Unsupported browser: " + browser);
		}
	}
}
