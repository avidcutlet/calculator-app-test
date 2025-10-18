package com.example.calculator.stepdefinitions;

import com.example.calculator.pages.LandingPage;
import com.example.calculator_manager.DriverManager;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.WebDriver;

public class UiSteps {

	WebDriver driver;

    private LandingPage landingPage;
    
    public UiSteps() {
        this.driver = DriverManager.getDriver();
        this.landingPage = new LandingPage(driver);
    }
    
    @Given("User is in the calculator page") 
    public void checkPage() {
    	assertTrue(landingPage.isHeaderTextVisible());
    }
    
    @Then("User should see the calculator elements") 
    public void verify() {
    	assertTrue(landingPage.getNumberOneElement().isDisplayed());
    	assertTrue(landingPage.getNumberTwoElement().isDisplayed());
    	assertTrue(landingPage.getOperationElement().isDisplayed());
    	assertTrue(landingPage.getComputeButtonElement().isDisplayed());
    	assertTrue(landingPage.getResultLabelElement().isDisplayed());
    }
}