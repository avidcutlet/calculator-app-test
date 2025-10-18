package com.example.calculator.stepdefinitions;

import com.example.calculator.utils.ConfigReader;
import io.cucumber.java.en.*;
import io.restassured.RestAssured; 
import io.restassured.response.Response;
import static org.assertj.core.api.Assertions.assertThat;

public class ApiSteps {
	
    private String baseUrl; 
    private Response response;
    
    @Given("I have the calculator base URL") 
    public void baseUrl() { 
    	baseUrl = ConfigReader.get("APP_URL");
	}
    
    @When("I send a GET request to the base URL") 
    public void get() { 
    	response = RestAssured.get(baseUrl); 
	}
    
    @Then("the response status should be {int}") 
    public void verify(Integer expected) { 
    	assertThat(response.statusCode()).isEqualTo(expected); 
	}
}