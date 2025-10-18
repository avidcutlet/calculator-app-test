package com.example.calculator.stepdefinitions;

import io.cucumber.java.en.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import com.example.calculator.pages.LandingPage;
import com.example.calculator_manager.DriverManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Cucumber steps that perform operations using the actual
 * web calculator at https://jmiguelcheq.github.io/calculator-demo
 */
public class UnitSteps {

	WebDriver driver;
    private LandingPage landingPage;
    private double a, b;
    private String operation;
    private double actualResult;
    
    public UnitSteps() {
        this.driver = DriverManager.getDriver();
        this.landingPage = new LandingPage(driver);
    }

    @Given("two numbers {double} and {double}")
    public void nums(double a, double b) {
        this.a = a;
        this.b = b;
    }

    @When("I {word} them")
    public void performOperation(String operation) {
    	landingPage.inputNumberOne(String.valueOf(a));
    	landingPage.inputNumberTwo(String.valueOf(b));
    	landingPage.selectOperation(operation);
    	landingPage.clickCompute();
    }

    @Then("the result should be {double}")
    public void verify(double expected) {
      assertThat(landingPage.getResult()).as("Verify calculator result equals expected").isCloseTo(expected, within(0.001));
    }
}
