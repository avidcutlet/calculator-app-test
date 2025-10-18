package com.example.calculator.testrunner;

import org.junit.runner.RunWith; 
import io.cucumber.junit.Cucumber; 
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
	    features = "src/test/resources/features/",
	    glue = {"com.example.calculator.stepdefinitions", "com.example.calculator.hooks", "example.cheq.calculator.listener"},
	    plugin = {"pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm", "com.example.calculator.listener.StepListener"},
	    monochrome = true
//		tags="@UI"
)
public class TestRunner {}
